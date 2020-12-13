import React from "react";
import Form from "./common/form";
import Joi from "joi";
import { resetPassword } from "../services/userService";

class ResetPasswordForm extends Form {
  state = {
    data: {
      email: "",
      to_new_password: "",
      new_password_confirm: "",
    },
    errors: {},
  };

  schema = Joi.object({
    email: Joi.string()
      .email({ tlds: false })
      .required()
      .label("Email address"),
    to_new_password: Joi.string().required().label("New Password"),
    new_password_confirm: Joi.any()
      .equal(Joi.ref("to_new_password"))
      .required()
      .label("Confirm new password")
      .options({
        messages: {
          "any.only": '"New Password" and {{#label}} does not match',
        },
      }),
  });

  doSubmit = async () => {
    try {
      const user = { ...this.state.data };
      delete user.new_password_confirm;
      await resetPassword(user);
      this.props.history.push("/auth/login");
    } catch (ex) {
      if (ex.response && ex.response.status === 404) {
        const errors = { ...this.state.errors };
        errors.email = ex.response.data;
        this.setState({ errors });
      }
    }
  };

  render() {
    return (
      <form className="RegisterForm" onSubmit={this.handleSubmit}>
        <h1>Reset Password Form</h1>
        {this.renderInput("email", "Email address")}
        {this.renderInput("to_new_password", "New Password", false, "password")}
        {this.renderInput(
          "new_password_confirm",
          "New Password Confirm",
          false,
          "password"
        )}
        {this.renderButton("Reset Password", "btn btn-dark")}
        <button
          onClick={() => this.props.history.goBack()}
          className="btn btn-light"
        >
          Back
        </button>
      </form>
    );
  }
}

export default ResetPasswordForm;