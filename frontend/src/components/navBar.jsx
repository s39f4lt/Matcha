import React from "react";
import { Link, NavLink } from "react-router-dom";
import "./styles/navBar.scss";

const NavBar = ({ user }) => {
  return (
    <nav className="navbar navbar-expand-lg navbar-light mb-3">
      <NavLink className="navbar-brand" to="/">
        Matcha
      </NavLink>
      <button
        className="navbar-toggler"
        type="button"
        data-toggle="collapse"
        data-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span className="navbar-toggler-icon"></span>
      </button>

      <div className="collapse navbar-collapse" id="navbarSupportedContent">
        <ul className="navbar-nav mr-auto">
          <li className="nav-item">
            <NavLink className="nav-link" to="/profile/me">
              My Profile
            </NavLink>
          </li>
          <li className="nav-item">
            <NavLink className="nav-link" to="/messages">
              Messages
            </NavLink>
          </li>
          <li className="nav-item">
            <NavLink className="nav-link" to="/search">
              Search
            </NavLink>
          </li>
        </ul>
        <form className="form-inline my-2 my-lg-0">
          <Link to="/logout">
            <button type="button" className="btn btn-danger">
              Logout
            </button>
          </Link>
        </form>
      </div>
    </nav>
  );
};

export default NavBar;
