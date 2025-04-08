package com.sielehub.treasuremart.presentation.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sielehub.treasuremart.domain.model.Name
import com.sielehub.treasuremart.domain.model.User
import com.sielehub.treasuremart.presentation.navigation.Route
import com.sielehub.treasuremart.presentation.ui.theme.TreasureMartTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.compose.KoinApplication
import org.koin.dsl.module

@Composable
fun SignupScreen(
    modifier: Modifier = Modifier,
    paddingValues: () -> PaddingValues = { PaddingValues() },
    naveController: () -> NavController,
) {
   // val authViewModel = koinViewModel<AuthViewModel>()
    val context = LocalContext.current
    var fullName by remember { mutableStateOf("John Doe") }
    var email by remember { mutableStateOf("John@gmail.com") }
    var username by remember { mutableStateOf("johnd") }
    var phone by remember { mutableStateOf("1-570-236-7033") }
    var password by remember { mutableStateOf("m38rmF$") }
    var confirmPassword by remember { mutableStateOf("m38rmF$") }
    var agreeTerms by remember { mutableStateOf(true) }
    var showPassword by remember { mutableStateOf(false) }
    var showConfirmPassword by remember { mutableStateOf(false) }

    var usernameValid by remember { mutableStateOf(isFieldValid("username", username)) }
    var emailValid by remember { mutableStateOf(isFieldValid("email", email)) }
    var passwordValid by remember { mutableStateOf(isFieldValid("password", password)) }
    var phoneValid by remember { mutableStateOf(isFieldValid("phone", phone)) }
    var fullNameValid by remember { mutableStateOf(isFieldValid("fullName", fullName)) }

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues())
    ) {
        val (upBtn, titleAndFields, txtHaveAccount, btnSignup) = createRefs()
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .constrainAs(titleAndFields) {
                    top.linkTo(parent.top)
                    bottom.linkTo(txtHaveAccount.top, 16.dp)
                    start.linkTo(parent.start, 20.dp)
                    end.linkTo(parent.end, 20.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
        ) {
            Spacer(modifier = modifier.height(70.dp))
            Text(
                text = "Welcome \nUser",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                ),
                modifier = modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = modifier.height(8.dp))
            Text(
                text = "Create account to continue",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Light,
                    fontSize = 18.sp
                ),
                modifier = modifier.fillMaxWidth()
            )
            Spacer(modifier = modifier.height(16.dp))

            TextField(
                modifier = modifier
                    .fillMaxWidth(),
                label = {
                    Text(text = "Full Name")
                },
                isError = !fullNameValid.first,
                supportingText = {
                    if (!fullNameValid.first)
                        Text(text = fullNameValid.second)
                },
                value = fullName,
                onValueChange = {
                    fullName = it
                },
                shape = MaterialTheme.shapes.small,
                singleLine = true,
                colors = TextFieldDefaults.colors().copy(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            Spacer(modifier = modifier.height(8.dp))
            TextField(
                modifier = modifier
                    .fillMaxWidth(),
                label = {
                    Text(text = "Email")
                },
                isError = !emailValid.first,
                supportingText = {
                    if (!fullNameValid.first)
                        Text(text = fullNameValid.second)
                },
                value = email,
                onValueChange = {
                    email = it
                },
                shape = MaterialTheme.shapes.small,
                singleLine = true,
                colors = TextFieldDefaults.colors().copy(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            Spacer(modifier = modifier.height(8.dp))
            TextField(
                modifier = modifier
                    .fillMaxWidth(),
                label = {
                    Text(text = "Username")
                },
                isError = !usernameValid.first,
                supportingText = {
                    if (!usernameValid.first)
                        Text(text = usernameValid.second)
                },
                value = username,
                onValueChange = {
                    username = it
                },
                shape = MaterialTheme.shapes.small,
                singleLine = true,
                colors = TextFieldDefaults.colors().copy(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            Spacer(modifier = modifier.height(8.dp))
            TextField(
                modifier = modifier
                    .fillMaxWidth(),
                label = {
                    Text(text = "Phone")
                },
                value = phone,
                onValueChange = {
                    phone = it
                },
                isError = !phoneValid.first,
                supportingText = {
                    if (!phoneValid.first)
                        Text(text = phoneValid.second)
                },
                shape = MaterialTheme.shapes.small,
                singleLine = true,
                colors = TextFieldDefaults.colors().copy(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            Spacer(modifier = modifier.height(8.dp))
            TextField(
                modifier = modifier
                    .fillMaxWidth(),
                label = {
                    Text(text = "Password")
                },
                value = password,
                onValueChange = {
                    password = it
                },
                isError = !passwordValid.first,
                supportingText = {
                    if (!passwordValid.first)
                        Text(text = passwordValid.second)
                },
                shape = MaterialTheme.shapes.small,
                singleLine = true,
                colors = TextFieldDefaults.colors().copy(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                trailingIcon = {
                    IconButton(onClick = { showPassword = !showPassword }) {
                        Icon(
                            imageVector = if (showPassword) Icons.Outlined.VisibilityOff
                            else Icons.Outlined.Visibility,
                            contentDescription = null
                        )
                    }
                },
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation()
            )
            Spacer(modifier = modifier.height(8.dp))
            TextField(
                modifier = modifier
                    .fillMaxWidth(),
                label = {
                    Text(text = "Confirm Password")
                },
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                },
                shape = MaterialTheme.shapes.small,
                singleLine = true,
                colors = TextFieldDefaults.colors().copy(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                trailingIcon = {
                    IconButton(onClick = { showConfirmPassword = !showConfirmPassword }) {

                        Icon(
                            imageVector = if (showConfirmPassword) Icons.Outlined.VisibilityOff
                            else Icons.Outlined.Visibility,
                            contentDescription = null
                        )
                    }
                },
                visualTransformation = if (showConfirmPassword) VisualTransformation.None else PasswordVisualTransformation()

            )
            Row(
                modifier = modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = agreeTerms,
                    onCheckedChange = {
                        agreeTerms = it
                    }
                )
                Text(
                    modifier = modifier
                        .padding(top = 8.dp),
                    text = buildAnnotatedString {
                        append("By creating an account you agree to our ")
                        withLink(
                            link = LinkAnnotation.Clickable(
                                tag = "Terms & Conditions",
                                linkInteractionListener = {

                                },
                                styles = TextLinkStyles(SpanStyle(fontWeight = FontWeight.Bold))
                            )
                        ) {
                            append("Terms & Conditions")
                        }
                    }
                )
            }
        }
        IconButton(
            modifier = modifier
                .background(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.9f),
                    shape = MaterialTheme.shapes.medium
                )
                .constrainAs(upBtn) {
                top.linkTo(parent.top, 16.dp)
                start.linkTo(parent.start, 16.dp)
            },
            onClick = {
                naveController().navigateUp()
            },
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }

        Text(
            modifier = modifier
                .constrainAs(txtHaveAccount) {
                    bottom.linkTo(btnSignup.top, 16.dp)
                    start.linkTo(parent.start, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                },
            style = MaterialTheme.typography.bodyLarge,
            text = buildAnnotatedString {
                append("Already have an account? ")
                withLink(
                    link = LinkAnnotation.Clickable(
                        tag = "Login",
                        styles = TextLinkStyles(
                            SpanStyle(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            ),
                        ),
                        linkInteractionListener = {
                            naveController().navigate(Route.Login)
                        }
                    )
                ) {
                    append("Login")
                }
            }
        )
        Button(
            modifier = modifier.constrainAs(btnSignup) {
                bottom.linkTo(parent.bottom, 24.dp)
                start.linkTo(parent.start, 20.dp)
                end.linkTo(parent.end, 20.dp)
                width = Dimension.fillToConstraints
            },
            onClick = {
                if (password != confirmPassword) {
                    confirmPassword = "Passwords do not match"
                }
                if (
                    agreeTerms
                    && isFieldValid("email", email).first
                    && isFieldValid("password", password).first
                    && isFieldValid("fullName", fullName).first
                ) {
                   /* authViewModel.createUser(
                        User(
                            name = Name(
                                firstname = fullName.substringBefore(" "),
                                lastname = fullName.substringBefore(" ")
                            ),
                            email = email,
                            password = password,
                            phone = phone,
                            username = username,
                        )
                    )*/
                } else {
                    Toast.makeText(
                        context,
                        "Ensure all fields a correctly filled",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        ) {
            Text(text = "Sign Up")
        }
    }
}

fun isFieldValid(field: String, value: String): Pair<Boolean, String> {
    return if (value.isEmpty()) {
        Pair(false, "$field cannot be empty")
    } else if (field == "fullName" && value.split(" ").size < 2) {
        Pair(false, "Full name must be at least 2 words")
    } else if (field == "email" && !value.contains("@")) {
        Pair(false, "Invalid email address")
    } else if (field == "password" && value.length < 6) {
        Pair(false, "Password is too short")
    } else if (field == "fullName" && value.split(" ").size < 2) {
        Pair(false, "Full name must be at least 2 words")
    } else {
        Pair(true, "")
    }
}

@Preview(showBackground = true)
@Composable
fun SignupScreenPreview() {
    val navController = rememberNavController()
    TreasureMartTheme(darkTheme = false) {
        //ScreenPreview {
            SignupScreen(naveController = { navController })
      //  }
   }
}

@Composable
fun ScreenPreview(
    screen: @Composable () -> Unit
) {
    val previewModule = module {
        viewModel { AuthViewModel(get(), get(), get(), get(), get()) }
    }
    KoinApplication(application = {
        modules(previewModule)
    }) { screen() }
}