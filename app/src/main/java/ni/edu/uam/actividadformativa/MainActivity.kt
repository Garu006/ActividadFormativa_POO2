package ni.edu.uam.actividadformativa

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import ni.edu.uam.actividadformativa.ui.theme.ActividadFormativaTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            var darkTheme by remember { mutableStateOf(false) }

            ActividadFormativaTheme(
                darkTheme = darkTheme
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ActividadScreen(
                        darkTheme = darkTheme,
                        onThemeChange = { darkTheme = it }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActividadScreen(
    darkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit
) {
    var textoIngresado by remember { mutableStateOf("") }
    var mensajeMostrado by remember { mutableStateOf("Aquí aparecerá el mensaje") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Actividad Formativa")
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Práctica con Jetpack Compose",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Cambiar a modo oscuro",
                style = MaterialTheme.typography.bodyLarge
            )

            Switch(
                checked = darkTheme,
                onCheckedChange = { onThemeChange(it) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = textoIngresado,
                onValueChange = { textoIngresado = it },
                label = { Text("Escribe un mensaje") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    mensajeMostrado = if (textoIngresado.isBlank()) {
                        "Por favor escribe algo primero"
                    } else {
                        "Texto ingresado: $textoIngresado"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Mostrar mensaje")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    launcher.launch("image/*")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Seleccionar imagen de galería")
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = mensajeMostrado,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Blue
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (imageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(imageUri),
                    contentDescription = "Imagen seleccionada",
                    modifier = Modifier.size(220.dp),
                    contentScale = ContentScale.Crop
                )
            } else {
                Text(
                    text = "No se ha seleccionado ninguna imagen",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ActividadScreenPreview() {
    ActividadFormativaTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ActividadScreen(
                darkTheme = false,
                onThemeChange = {}
            )
        }
    }
}