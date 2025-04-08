package com.sielehub.treasuremart.presentation.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sielehub.treasuremart.presentation.ui.theme.TreasureMartTheme

@Composable
fun ForgotPasswordScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    paddingValues: PaddingValues = PaddingValues()
) {

    //val authViewModel = koinViewModel<AuthViewModel>()
    val context = LocalContext.current
    var email by remember { mutableStateOf("John@gmail.com") }
    var isEmailValid by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        IconButton(
            modifier = modifier
                .padding(start = 16.dp, top = 16.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.9f),
                    shape = MaterialTheme.shapes.medium
                )
                ,
            onClick = {
                navController.popBackStack()
            },
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }

        Text(
            text = "Reset \nPassword",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            ),
            modifier = modifier
                .padding(start = 16.dp, top = 16.dp)
        )
        Text(
            text = "Enter your email to  receive " +
                    "password reset instructions",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Light,
                fontSize = 18.sp
            ),
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 10.dp, end = 16.dp)
        )
        Spacer(modifier = modifier.height(36.dp))
        TextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            label = {
                Text(text = "Email address")
            },
            value = email,
            onValueChange = {
                email = it
            },
            shape = MaterialTheme.shapes.small,
            singleLine = true,
            isError = !isEmailValid,
            colors = TextFieldDefaults.colors().copy(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
            )
        )
        Spacer(modifier = modifier.height(36.dp))
        Button(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = {
                if (email.isNotEmpty() && email.contains("@")) {
                    Toast.makeText(context, "Password reset email sent", Toast.LENGTH_SHORT).show()
                } else {
                    isEmailValid = false
                    Toast.makeText(context, "Invalid email", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text(text = "Reset")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ForgotPasswordScreenPreview() {
    TreasureMartTheme {
        ForgotPasswordScreen(navController = rememberNavController())
    }
}