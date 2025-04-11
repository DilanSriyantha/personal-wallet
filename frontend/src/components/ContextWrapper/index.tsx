import { CssBaseline, ThemeProvider } from "@mui/material";
import theme from "../../theme/theme";
import { ReactNode } from "react";

interface ContextWrapperProps {
    children: ReactNode;
};

function ContextWrapper({ children }: ContextWrapperProps) {

    return(
        <>
            <ThemeProvider theme={theme}>
                <CssBaseline />
                { children }
            </ThemeProvider>
        </>
    );
}

export default ContextWrapper;