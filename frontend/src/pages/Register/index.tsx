import { Box, Button, Card, Container, Stack, TextField, Typography } from "@mui/material";
import { ChangeEvent, useCallback, useReducer, useState } from "react";
import { RegisterRequest } from "../../types/types";

const initialState: RegisterRequest = {
    name: "",
    email: "",
    password: ""
};

enum ActionType {
    SET_FIELD,
};

const reducer = (state: RegisterRequest, action: { type: ActionType, payload: any }): RegisterRequest => {
    switch(action.type){
        case ActionType.SET_FIELD:
            return { ...state, [action.payload.name]: action.payload.value };
        default:
            return state;
    }
};

function Register() {
    const [state, dispatch] = useReducer(reducer, initialState);

    const [isLoading, setIsLoading] = useState<boolean>(false);

    const handleRegisterClick = useCallback(async () => {
        try{
            setIsLoading(true);
            const res = await fetch("https://kkz02nvr-8080.asse.devtunnels.ms/api/v1/auth/register", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(state)
            });
            if(res){
                console.log(await res.json());
                setIsLoading(false);
            }
        }catch(err){
            console.log(err);
            setIsLoading(false);
        }
    }, [state]);

    const handleTextFieldChange = useCallback((event: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>): void => {
        dispatch({ type: ActionType.SET_FIELD, payload: { name: event.target.name, value: event.target.value } });
    }, []);

    return(
        <Container maxWidth={"sm"}>
            <Card elevation={0}>
                <Box sx={{ placeItems: "center" }}>
                    <Typography variant="h6">Register</Typography>
                </Box>
                <Stack direction={"column"} gap={1}>
                    <TextField variant={"outlined"} label={"Name"} name={"name"} type={"text"} onChange={handleTextFieldChange}/>
                    <TextField variant={"outlined"} label={"E-Mail"} name={"email"} type={"email"} onChange={handleTextFieldChange}/>
                    <TextField variant={"outlined"} label={"Password"} name={"password"} type={"password"} onChange={handleTextFieldChange}/>
                    <Button variant={"contained"} loading={isLoading} loadingPosition={"start"} onClick={handleRegisterClick}>Register</Button>
                </Stack>
            </Card>
        </Container>
    );
}

export default Register;