package com.sielehub.treasuremart.presentation.ui.auth

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sielehub.treasuremart.R
import com.sielehub.treasuremart.presentation.ui.navigation.Route
import com.sielehub.treasuremart.presentation.ui.theme.TreasureMartTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    paddingValues: () -> PaddingValues = { PaddingValues() },
    navController: () -> NavController,
    onSignUp: () -> Unit = {},
    onForgotPassword: () -> Unit = {},
) {
    val context = LocalContext.current
    val authViewModel = koinViewModel<AuthViewModel>()
    var username by remember { mutableStateOf("johnd") }
    var password by remember { mutableStateOf("m38rmF$") }
    var showPassword by remember { mutableStateOf(false) }
    var usernameValid by remember {
        mutableStateOf(
            isFieldValid(
                context.getString(R.string.username_label).lowercase(),
                username, context
            )
        )
    }
    var passwordValid by remember {
        mutableStateOf(
            isFieldValid(
                context.getString(R.string.password_label).lowercase(),
                password,
                context
            )
        )
    }
    val loginState by authViewModel.loginState.collectAsStateWithLifecycle()
    val token by authViewModel.authToken.collectAsStateWithLifecycle()
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues())
    ) {
        val (
            txtWelcome,
            txtLabel,
            txtUsername,
            txtPassword,
            txtForgotPassword,
            txtNoAccount,
            btnLogin
        ) = createRefs()

        Text(
            text = stringResource(R.string.welcome_back),
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            ),
            modifier = modifier.constrainAs(txtWelcome) {
                top.linkTo(parent.top, 100.dp)
                start.linkTo(parent.start, 20.dp)
            }
        )

        Text(
            text = stringResource(R.string.login_to_continue),
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Light,
                fontSize = 18.sp
            ),
            modifier = modifier.constrainAs(txtLabel) {
                top.linkTo(txtWelcome.bottom, 10.dp)
                start.linkTo(parent.start, 20.dp)
            }
        )

        TextField(
            modifier = modifier
                .constrainAs(txtUsername) {
                    top.linkTo(txtLabel.bottom, 36.dp)
                    start.linkTo(parent.start, 20.dp)
                    end.linkTo(parent.end, 20.dp)
                    width = Dimension.fillToConstraints
                },
            label = {
                Text(text = stringResource(R.string.username_label))
            },
            value = username,
            onValueChange = {
                username = it
            },
            isError = !usernameValid.first,
            supportingText = {
                if (!usernameValid.first)
                    Text(
                        text = usernameValid.second,
                        color = MaterialTheme.colorScheme.error
                    )
            },
            shape = MaterialTheme.shapes.small,
            singleLine = true,
            colors = TextFieldDefaults.colors().copy(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        TextField(
            modifier = modifier
                .constrainAs(txtPassword) {
                    top.linkTo(txtUsername.bottom, 24.dp)
                    start.linkTo(parent.start, 20.dp)
                    end.linkTo(parent.end, 20.dp)
                    width = Dimension.fillToConstraints
                },
            label = {
                Text(text = stringResource(R.string.password_label))
            },
            value = password,
            onValueChange = {
                password = it
            },
            isError = !passwordValid.first,
            supportingText = {
                if (!passwordValid.first)
                    Text(
                        text = passwordValid.second,
                        color = MaterialTheme.colorScheme.error
                    )
            },
            shape = MaterialTheme.shapes.small,
            singleLine = true,
            colors = TextFieldDefaults.colors().copy(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { showPassword = !showPassword }) {
                    Icon(
                        imageVector = if (showPassword) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                        contentDescription = null
                    )
                }
            }
        )

        Text(
            text = stringResource(R.string.forgot_password),
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifier
                .clickable {
                    onForgotPassword()
                }
                .constrainAs(txtForgotPassword) {
                    top.linkTo(txtPassword.bottom, 8.dp)
                    end.linkTo(parent.end, 20.dp)
                })

        Text(
            modifier = modifier
                .constrainAs(txtNoAccount) {
                    bottom.linkTo(btnLogin.top, 30.dp)
                    start.linkTo(parent.start, 20.dp)
                    end.linkTo(parent.end, 20.dp)
                },
            style = MaterialTheme.typography.bodyLarge,
            text = buildAnnotatedString {
                append(stringResource(R.string.don_t_have_an_account))
                withLink(
                    link = LinkAnnotation.Clickable(
                        tag = stringResource(R.string.sign_up),
                        styles = TextLinkStyles(
                            SpanStyle(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            ),
                        ),
                        linkInteractionListener = { onSignUp() }
                    )) {
                    append(stringResource(R.string.sign_up))
                }
            }
        )

        Button(
            modifier = modifier
                .height(56.dp)
                .constrainAs(btnLogin) {
                    bottom.linkTo(parent.bottom, 24.dp)
                    start.linkTo(parent.start, 20.dp)
                    end.linkTo(parent.end, 20.dp)
                    width = Dimension.fillToConstraints
                },
            onClick = {
                usernameValid = isFieldValid(
                    context.getString(R.string.username_label).lowercase(),
                    username,
                    context
                )
                passwordValid = isFieldValid(
                    context.getString(R.string.password_label).lowercase(),
                    password,
                    context
                )
                if (usernameValid.first && passwordValid.first) {
                    authViewModel.login(username, password)
                }
            }
        ) {
            if (loginState.isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = modifier.size(30.dp),
                    strokeWidth = 2.dp
                )
                Spacer(modifier = modifier.size(16.dp))
            }
            Text(
                text = stringResource(R.string.login),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }

    LaunchedEffect(loginState) {
        if (loginState.token != null && token == loginState.token) {
            navController().navigate(Route.Dashboard) {
                popUpTo(Route.Auth) {
                    inclusive = true
                }
            }
        }
        if (loginState.error.isNullOrEmpty().not()) {
            Toast.makeText(context, loginState.error, Toast.LENGTH_SHORT).show()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    val navController = rememberNavController()
    TreasureMartTheme(darkTheme = false) {
        ScreenPreview {
            LoginScreen(navController = { navController })
        }
    }
}