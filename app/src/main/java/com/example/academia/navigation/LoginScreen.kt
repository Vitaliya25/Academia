package com.example.academia.navigation
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.util.PatternsCompat
import com.example.academia.R
import com.example.academia.model.User
import com.example.academia.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



@Composable
fun LoginScreen(navigateTo: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxSize()
            .background(colorResource(id = R.color.fondoOscuro))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Iniciar Sesión",
            color = colorResource(id = R.color.tex1),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
            )
        Spacer(modifier = Modifier.height(32.dp))

        // Campo de correo electrónico
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                emailError = if (PatternsCompat.EMAIL_ADDRESS.matcher(it).matches()) null else "Correo no válido"
            },
            label = {
                Text(
                    "Correo electrónico",
                    fontSize = 18.sp, // Cambia el tamaño del texto del label
                    fontWeight = FontWeight.Bold // Opcional: hacer el texto más grueso
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,   // Color de fondo cuando está enfocado
                unfocusedContainerColor = Color.Transparent, // Color de fondo cuando no está enfocado
                focusedIndicatorColor = Color.White,         // Color del borde cuando está enfocado
                unfocusedIndicatorColor = Color.White,       // Color del borde cuando no está enfocado
                focusedLabelColor = Color.White,            // Color del label cuando está enfocado
                unfocusedLabelColor = Color.White,      // Color del label cuando no está enfocado
                cursorColor = Color.White                    // Color del cursor
            ),
            isError = emailError != null,
            modifier = Modifier.fillMaxWidth()
        )
        emailError?.let { Text(it, color = Color.Red, style = MaterialTheme.typography.bodySmall) }

        Spacer(modifier = Modifier.height(8.dp))

        // Campo de contraseña
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                passwordError = if (it.length >= 6) null else "Mínimo 6 caracteres"
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,   // Color de fondo cuando está enfocado
                unfocusedContainerColor = Color.Transparent, // Color de fondo cuando no está enfocado
                focusedIndicatorColor = Color.White,         // Color del borde cuando está enfocado
                unfocusedIndicatorColor = Color.White,       // Color del borde cuando no está enfocado
                focusedLabelColor = Color.White,            // Color del label cuando está enfocado
                unfocusedLabelColor = Color.White,      // Color del label cuando no está enfocado
                cursorColor = Color.White                    // Color del cursor
            ),
            label = {
                Text(
                    "Contraseña",
                    fontSize = 18.sp, // Cambia el tamaño del texto del label
                    fontWeight = FontWeight.Bold // Opcional: hacer el texto más grueso
                )
            },
            visualTransformation = PasswordVisualTransformation(),
            isError = passwordError != null,
            modifier = Modifier.fillMaxWidth()
        )
        passwordError?.let { Text(it, color = Color.Red, style = MaterialTheme.typography.bodySmall) }

        Spacer(modifier = Modifier.height(16.dp))

//        Button(
//            onClick = { navigateTo() },
//            modifier = Modifier.fillMaxWidth(),
//            colors = ButtonDefaults.buttonColors(
//                containerColor = colorResource(id = R.color.fondoClaro), // Color de fondo
//                contentColor = colorResource(id = R.color.fondoOscuro)     // Color del texto
//            )
//        ) {
//            Text(
//                text = "Iniciar sesión",
//                fontSize = 24.sp
//            )
//        }

        Button(onClick = {
            if (emailError == null && passwordError == null) {
                validarUsuario(email, password,
                    onSuccess = {
                        Log.d("Login", "Bienvenido ${it.username}")
                        navigateTo()
                    },
                    onError = {
                        Log.e("Login", "Usuario o contraseña incorrectos")
                    }
                )
            }
        }) {
            Text("Iniciar sesión")
        }


        Spacer(modifier = Modifier.height(8.dp))

        // Opción para recuperar contraseña
        TextButton(onClick = { /* recuperar contraseña */ }) {
            Text(text = "¿Olvidaste tu contraseña?",
                fontSize = 18.sp,
                color = colorResource(id = R.color.tex1)
                )
        }
    }
}
fun validarUsuario(
    email: String,
    password: String,
    onSuccess: (User) -> Unit,
    onError: () -> Unit
) {
    RetrofitClient.instance.obtenerUsuarios().enqueue(object : Callback<List<User>> {
        override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
            if (response.isSuccessful) {
                val usuarios = response.body()
                val user = usuarios?.find { it.email == email && it.password == password }
                if (user != null) {
                    onSuccess(user)
                } else {
                    onError()
                }
            } else {
                onError()
            }
        }

        override fun onFailure(call: Call<List<User>>, t: Throwable) {
            onError()
        }
    })
}

