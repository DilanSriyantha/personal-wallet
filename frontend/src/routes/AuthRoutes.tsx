import { lazy } from "react";
import RouteLoader from "./RouteLoader";
import { RouteObject } from "react-router";
import BaseLayout from "../layouts/BaseLayout";

const Register = RouteLoader(lazy(() => import("../pages/Register")));
const Login = RouteLoader(lazy(() => import("../pages/Login")));

const AuthRoutes: RouteObject[] = [
    {
        path: "/",
        element: <BaseLayout />,
        children: [
            {
                path: "",
                element: <Login />
            },
            {
                path: "register",
                element: <Register />
            }
        ]
    }
];

export default AuthRoutes;