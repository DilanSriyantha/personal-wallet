import { Box, Button, Card, Container, Stack, TextField, Typography } from "@mui/material";

function Login() {

    return (
        <Container maxWidth={"sm"}>
            <Stack direction={"column"} gap={1}>
                <Box sx={{ placeItems: "center" }}>
                    <Typography variant="h6">Login</Typography>
                </Box>
                <Card elevation={0}>
                    <Stack direction={"column"} gap={1}>
                        <TextField variant="outlined" label="E-mail" type="email" />
                        <TextField variant="outlined" label="Password" type="password" />
                        <Button variant="contained" loadingPosition="start">Login</Button>
                    </Stack>
                </Card>
            </Stack>
        </Container>
    );
}

export default Login;