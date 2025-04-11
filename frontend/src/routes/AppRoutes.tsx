import { useMemo } from "react";
import AuthRoutes from "./AuthRoutes";
import { useRoutes } from "react-router";

function AppRoutes() {

    const authRoutes = useMemo(() => AuthRoutes, []);

    return useRoutes(authRoutes);
}

export default AppRoutes;