export interface RegisterRequest {
    name: string;
    email: string;
    password: string;
};

export interface AuthResponse {
    name: string;
    email: string;
    accessToken: string;
    refreshToken: string;
};