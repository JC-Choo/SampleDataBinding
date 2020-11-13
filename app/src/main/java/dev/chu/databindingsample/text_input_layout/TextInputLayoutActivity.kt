package dev.chu.databindingsample.text_input_layout

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import dev.chu.databindingsample.R
import dev.chu.databindingsample.databinding.ActivityTextInputLayoutBinding

/**
 * 참고 : https://medium.com/better-programming/textinputlayout-form-validation-using-data-binding-in-android-86aea8645a11
 */
class TextInputLayoutActivity : AppCompatActivity() {

    private var _binding: ActivityTextInputLayoutBinding? = null
    private val binding: ActivityTextInputLayoutBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_text_input_layout)
        binding.lifecycleOwner = this

        setupListeners()

        binding.loginButton.setOnClickListener {
            if (isValidate()) {
                Toast.makeText(this, "validated", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValidate(): Boolean =
        validateUserName() && validateEmail() && validatePassword() && validateConfirmPassword()

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupListeners() {
        binding.userName.addTextChangedListener(TextFieldValidation(binding.userName))
        binding.email.addTextChangedListener(TextFieldValidation(binding.email))
        binding.password.addTextChangedListener(TextFieldValidation(binding.password))
        binding.confirmPassword.addTextChangedListener(TextFieldValidation(binding.confirmPassword))
    }

    /**
     * field must not be empy
     */
    private fun validateUserName(): Boolean {
        if (binding.userName.text.toString().trim().isEmpty()) {
            binding.userNameTextInputLayout.error = "Required Field!"
            binding.userName.requestFocus()
            return false
        } else {
            binding.userNameTextInputLayout.isErrorEnabled = false
        }
        return true
    }

    /**
     * 1) field must not be empty
     * 2) text should matches email address format
     */
    private fun validateEmail(): Boolean {
        if (binding.email.text.toString().trim().isEmpty()) {
            binding.emailTextInputLayout.error = "Required Field!"
            binding.email.requestFocus()
            return false
        } else if (!isValidEmail(binding.email.text.toString())) {
            binding.emailTextInputLayout.error = "Invalid Email!"
            binding.email.requestFocus()
            return false
        } else {
            binding.emailTextInputLayout.isErrorEnabled = false
        }
        return true
    }

    /**
     * 1) field must not be empty
     * 2) password lenght must not be less than 6
     * 3) password must contain at least one digit
     * 4) password must contain atleast one upper and one lower case letter
     * 5) password must contain atleast one special character.
     */
    private fun validatePassword(): Boolean {
        if (binding.password.text.toString().trim().isEmpty()) {
            binding.passwordTextInputLayout.error = "Required Field!"
            binding.password.requestFocus()
            return false
        } else if (binding.password.text.toString().length < 6) {
            binding.passwordTextInputLayout.error = "password can't be less than 6"
            binding.password.requestFocus()
            return false
        } else if (!isStringContainNumber(binding.password.text.toString())) {
            binding.passwordTextInputLayout.error = "Required at least 1 digit"
            binding.password.requestFocus()
            return false
        } else if (!isStringLowerAndUpperCase(binding.password.text.toString())) {
            binding.passwordTextInputLayout.error =
                "Password must contain upper and lower case letters"
            binding.password.requestFocus()
            return false
        } else if (!isStringContainSpecialCharacter(binding.password.text.toString())) {
            binding.passwordTextInputLayout.error = "1 special character required"
            binding.password.requestFocus()
            return false
        } else {
            binding.passwordTextInputLayout.isErrorEnabled = false
        }
        return true
    }

    /**
     * 1) field must not be empty
     * 2) password and confirm password should be same
     */
    private fun validateConfirmPassword(): Boolean {
        when {
            binding.confirmPassword.text.toString().trim().isEmpty() -> {
                binding.confirmPasswordTextInputLayout.error = "Required Field!"
                binding.confirmPassword.requestFocus()
                return false
            }
            binding.confirmPassword.text.toString() != binding.password.text.toString() -> {
                binding.confirmPasswordTextInputLayout.error = "Passwords don't match"
                binding.confirmPassword.requestFocus()
                return false
            }
            else -> {
                binding.confirmPasswordTextInputLayout.isErrorEnabled = false
            }
        }
        return true
    }

    inner class TextFieldValidation(private val view: View) : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            // checking ids of each text field and applying functions accordingly.
            when (view.id) {
                R.id.userName -> {
                    validateUserName()
                }
                R.id.email -> {
                    validateEmail()
                }
                R.id.password -> {
                    validatePassword()
                }
                R.id.confirmPassword -> {
                    validateConfirmPassword()
                }
            }
        }

        override fun afterTextChanged(p0: Editable?) {}
    }
}