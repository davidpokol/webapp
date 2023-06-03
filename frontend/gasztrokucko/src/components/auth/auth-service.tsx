import { position } from "@chakra-ui/react";
import axios, { AxiosError } from "axios";
import { useToast } from '@chakra-ui/react'

const TOKEN = 'authToken';
const REMEMBER_ME = 'remember';
const API_URL = '/users';

class AuthServiceImpl {
    private storage: Storage;
    private localStorage: Storage;

    constructor() {
        this.storage = window.sessionStorage;
        this.localStorage = window.localStorage;
    }

    public async login(username: string, password: string, rememberMe: boolean): Promise<string> {
        const res = await axios.post(`/authentication`, {
            username,
            password
        });

        if (res.status !== 200) {
            throw new Error();
        }

        const authToken = await res.data;
        this.authToken = authToken;

        if (rememberMe) {
            this.userDetails = username + ";" + password + ";" + rememberMe;
            console.log(this.userDetails);
        }

        return authToken;
    }

    public logout() {
        console.log("TOKEN" + this.authToken);
        this.authToken = null;
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

    public get userDetails(): string | null {
        return this.localStorage.getItem(REMEMBER_ME) ?? null;
    }

    public set userDetails(data: string | null) {
        if (data) {
            this.localStorage.setItem(REMEMBER_ME, data);
        } else if (this.userDetails) {
            this.localStorage.removeItem(REMEMBER_ME);
        }
    }

    public async registration(username: string, email: string, password: string, toast: ReturnType<typeof useToast>) {
        const res = await axios.post(`${API_URL}/signUp`, {
            username,
            email,
            password
        });

        if (res.status === 201) {
            toast({
                title: 'Regisztráció',
                description: "Sikeres regisztráció!",
                status: 'success',
                position: 'top',
                duration: 9000,
                isClosable: true,
            });
        }
    }

}

export const AuthService = new AuthServiceImpl();