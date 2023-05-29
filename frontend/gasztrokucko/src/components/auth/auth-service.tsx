import { position } from "@chakra-ui/react";
import axios from "axios";
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const TOKEN = 'authToken';
const API_URL = '/users';

class AuthServiceImpl {
    private storage: Storage;

    constructor() {
        this.storage = window.sessionStorage;
    }

    public async login(username: string, password: string): Promise<string> {
        const res = await axios.post(`${API_URL}/login`, {
            username,
            password
        });

        if (res.status !== 200) {
            toast.error('Helytelen felhasználónév-jelszó páros!');
        }

        const { authToken } = await res.data;
        this.authToken = authToken;
        return authToken;
    }

    public get authToken(): string | null {
        return this.storage.getItem(TOKEN) ?? null;
    }

    public set authToken(token: string | null) {
        if (token) {
            this.storage.setItem(TOKEN, token);
        } else if (this.authToken) {
            this.storage.removeItem(TOKEN);
        }
    }

    public async registration(username: string, email: string, password: string) {
        const res = await axios.post(`${API_URL}/signUp`, {
            username,
            email,
            password
        });

        if (res.status !== 201) {
            toast.error('Helytelen regisztrációs adatok!');
        }
    }

}

export const AuthService = new AuthServiceImpl();