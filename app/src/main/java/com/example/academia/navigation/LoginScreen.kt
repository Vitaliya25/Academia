package com.example.academia.navigation
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.util.PatternsCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.academia.R
import com.example.academia.viewmodel.UsuarioViewModel
@Composable
fun LoginScreen(
    navigateTo: () -> Unit,
    usuarioViewModel: UsuarioViewModel = viewModel()
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginError by remember { mutableStateOf<String?>(null) }
    var submitClicked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.fondoOscuro))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Iniciar Sesión",
            color = colorResource(id = R.color.textoBlanco),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = {
                Text("Nombre de usuario", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.White,
                unfocusedIndicatorColor = Color.White,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White,
                cursorColor = Color.White
            ),
            textStyle = TextStyle(
                color = Color.White,  // Aquí cambias el color del texto que se escribe
                fontSize = 24.sp       // Aquí cambias el tamaño del texto
            ),
            modifier = Modifier.fillMaxWidth()
        )
        if (submitClicked && username.isBlank()) {
            Text("Este campo es obligatorio", color = Color.Red, style = MaterialTheme.typography.bodySmall)
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = {
                Text("Contraseña", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            },
            visualTransformation = PasswordVisualTransformation(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.White,
                unfocusedIndicatorColor = Color.White,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White,
                cursorColor = Color.White
            ),
            textStyle = TextStyle(
                color = Color.White,  // Aquí cambias el color del texto que se escribe
                fontSize = 24.sp       // Aquí cambias el tamaño del texto
            ),
            modifier = Modifier.fillMaxWidth()
        )
        if (submitClicked && password.length < 6) {
            Text("Mínimo 6 caracteres", color = Color.DarkGray, fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                submitClicked = true
                val usuarioValido = username.isNotBlank()
                val passwordValida = password.length >= 6

                if (usuarioValido && passwordValida) {
                    usuarioViewModel.login(
                        username,
                        password,
                        onSuccess = {
                            loginError = null
                            navigateTo()
                        },
                        onError = {
                            loginError = "Nombre de usuario o contraseña incorrectos"
                        }
                    )
                } else {
                    loginError = "Por favor, completa los campos correctamente"
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.fondoClaro),
                contentColor = colorResource(id = R.color.fondoOscuro)
            )
        ) {
            Text("Iniciar sesión", fontSize = 24.sp)
        }

        loginError?.let {
            Spacer(modifier = Modifier.height(10.dp))
            Text(it, color = Color.DarkGray, fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = { /* recuperar contraseña */ }) {
            Text("¿Olvidaste tu contraseña?", fontSize = 18.sp, color = colorResource(id = R.color.textoBlanco))
        }
    }
}
