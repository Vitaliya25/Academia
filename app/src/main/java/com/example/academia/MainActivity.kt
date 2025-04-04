package com.example.academia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.academia.ui.theme.ComposeCatalogTheme
import com.example.academia.navigation.NavigationWrapper

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeCatalogTheme {
                NavigationWrapper()
                    }
                    }
                }
            }




