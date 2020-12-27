import React, { useEffect, useState } from "react";
import { Redirect, Route, Switch } from "react-router-dom";
import Logout from "../components/logout";
import NavBar from "../components/navBar";
import HomePage from "../pages/homePage";
import ProfilePage from "../pages/profilePage";
import MessagesPage from "./messagesPage";
import NotFound from "../components/notFound";
import SearchPage from "./searchPage";
import auth, { getJwt } from "../services/authService";
import { ws } from "../config.json";
import { toast } from "react-toastify";
import UserNotification from "../components/userNotification";
import BaseContext from "../contexts/baseContext";
import jquery from "jquery";
import httpService from "../services/httpService";

const BasePage = (props) => {
  const [user, setUser] = useState({});
  const [webSocket, setWebSocket] = useState(null);

  useEffect(() => {
    const user = auth.getCurrentUser();
    jquery.getJSON("https://api.ipify.org?format=json", function (data) {
      console.log(data.ip);
      httpService.get(`http://ip-api.com/json/${data.ip}`).then(console.log);
    });
    setWebSocket(new WebSocket(`${ws}T_${getJwt()}`));
    setUser(user);
  }, []);

  useEffect(() => {
    if (webSocket) {
      webSocket.onopen = () => {
        console.log("connected");
      };

      webSocket.onclose = () => {
        console.log("closed");
      };

      webSocket.onmessage = (message) => {
        const data = JSON.parse(message.data);
        if (data.type !== "system_notification") {
          toast(
            <UserNotification
              dataType={data.type}
              avatarLink={data.avatar?.link}
              username={data.username}
              message={data.message}
            />
          );
        }
      };

      return () => {
        webSocket.close();
        console.log("closed");
      };
    }
  }, [webSocket, props.match.pathname]);

  return (
    <>
      <BaseContext.Provider value={{ webSocket }}>
        <NavBar user={user} />
        <main className="container">
          <Switch>
            <Route path="/profile/:username" component={ProfilePage} />
            <Route path="/messages/:username?" component={MessagesPage} />
            <Route path="/search" component={SearchPage} />
            <Route path="/logout" component={Logout} />
            <Route path="/not-found" component={NotFound} />
            <Route path="/" exact component={HomePage} />
            <Redirect to="/not-found" />
          </Switch>
        </main>
      </BaseContext.Provider>
    </>
  );
};

export default BasePage;
