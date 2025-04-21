package com.sielehub.treasuremart.presentation.ui.auth

import android.content.Context
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sielehub.treasuremart.R
import com.sielehub.treasuremart.domain.model.SignupRequest
import com.sielehub.treasuremart.presentation.ui.navigation.Route
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
    val authViewModel = koinViewModel<AuthViewModel>()
    val context = LocalContext.current
    var email by remember { mutableStateOf("John@gmail.com") }
    var username by remember { mutableStateOf("johnd") }
    var password by remember { mutableStateOf("m38rmF$") }
    var confirmPassword by remember { mutableStateOf("m38rmF$") }
    var agreeTerms by remember { mutableStateOf(true) }
    var showPassword by remember { mutableStateOf(false) }
    var showConfirmPassword by remember { mutableStateOf(false) }

    var usernameValid by remember {
        mutableStateOf(
            isFieldValid(
                context.getString(R.string.username_label).lowercase(), username, context
            )
        )
    }
    var emailValid by remember {
        mutableStateOf(
            isFieldValid(
                context.getString(R.string.email_label).lowercase(), email, context
            )
        )
    }
    var passwordValid by remember {
        mutableStateOf(
            isFieldValid(
                context.getString(R.string.password_label).lowercase(), password, context
            )
        )
    }
    var newUser by remember {
        mutableStateOf(
            SignupRequest(
                username = username,
                email = email,
                id = 1,
                password = password
            )
        )
    }

    val signupState by authViewModel.signupState

    LaunchedEffect(signupState) {
        if (signupState.signupRequest != null && newUser == signupState.signupRequest) {
            Toast.makeText(
                context,
                context.getString(R.string.signup_successful),
                Toast.LENGTH_SHORT
            ).show()
            naveController().navigate(Route.Login) {
                popUpTo(Route.Signup) {
                    inclusive = true
                }
            }
        }
    }

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
            Spacer(modifier = modifier.height(90.dp))
            Text(
                text = stringResource(R.string.welcome_user),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                ),
                modifier = modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = modifier.height(8.dp))
            Text(
                text = stringResource(R.string.create_account_to_continue),
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
                    Text(text = stringResource(R.string.username_label))
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
                    Text(text = stringResource(R.string.email_label))
                },
                isError = !emailValid.first,
                supportingText = {
                    if (!emailValid.first)
                        Text(text = emailValid.second)
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
                    Text(text = stringResource(R.string.password_label))
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
                    Text(text = stringResource(R.string.confirm_password))
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
                visualTransformation = if (showConfirmPassword) VisualTransformation.None
                else PasswordVisualTransformation()
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
                        append(stringResource(R.string.agree_to_terms))
                        withLink(
                            link = LinkAnnotation.Clickable(
                                tag = stringResource(R.string.terms_conditions),
                                linkInteractionListener = {

                                },
                                styles = TextLinkStyles(
                                    SpanStyle(
                                        fontWeight = FontWeight.Bold,
                                        textDecoration = TextDecoration.Underline,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                )
                            )
                        ) {
                            append(stringResource(R.string.terms_conditions))
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
                append(stringResource(R.string.already_have_an_account))
                withLink(
                    link = LinkAnnotation.Clickable(
                        tag = stringResource(R.string.login),
                        styles = TextLinkStyles(
                            SpanStyle(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                textDecoration = TextDecoration.Underline
                            ),
                        ),
                        linkInteractionListener = {
                            naveController().navigate(Route.Login)
                        }
                    )
                ) {
                    append(stringResource(R.string.login))
                }
            }
        )
        Button(
            modifier = modifier
                .height(56.dp)
                .constrainAs(btnSignup) {
                    bottom.linkTo(parent.bottom, 24.dp)
                    start.linkTo(parent.start, 20.dp)
                    end.linkTo(parent.end, 20.dp)
                    width = Dimension.fillToConstraints
                },
            onClick = {
                if (password != confirmPassword) {
                    confirmPassword = context.getString(R.string.passwords_do_not_match)
                }
                if (
                    agreeTerms
                    && isFieldValid(
                        context.getString(R.string.username_label).lowercase(),
                        username,
                        context
                    ).first
                    && isFieldValid(
                        context.getString(R.string.email_label).lowercase(),
                        email,
                        context
                    ).first
                    && isFieldValid(
                        context.getString(R.string.password_label).lowercase(),
                        password,
                        context
                    ).first
                ) {
                    newUser = SignupRequest(
                        id = 1,
                        email = email,
                        password = password,
                        username = username,
                    )
                    authViewModel.createUser(newUser)
                } else {
                    Toast.makeText(
                        context,
                        context.getString(R.string.ensure_all_fields_a_correctly_filled),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        ) {
            when {
                signupState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = modifier.size(30.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = modifier.width(8.dp))
                }

                signupState.error.isNotEmpty() -> {
                    Toast.makeText(context, signupState.error, Toast.LENGTH_SHORT).show()
                }
            }
            Text(
                text = stringResource(R.string.sign_up),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

fun isFieldValid(field: String, value: String, context: Context): Pair<Boolean, String> {
    return when {
        value.isEmpty() -> {
            Pair(false, context.getString(R.string.field_cannot_be_empty, field))
        }

        field == context.getString(R.string.username_label)
            .lowercase() && value.length < 4
            -> {
            Pair(false, context.getString(R.string.username_is_too_short))
        }

        field == context.getString(R.string.email_label)
            .lowercase() && !value.contains("@")
            -> {
            Pair(false, context.getString(R.string.invalid_email_address))
        }

        field == context.getString(R.string.password_label)
            .lowercase() && value.length < 6
            -> {
            Pair(false, context.getString(R.string.password_is_too_short))
        }

        else -> {
            Pair(true, "")
        }
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