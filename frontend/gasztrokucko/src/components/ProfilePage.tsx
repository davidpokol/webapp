import React, { useEffect, useState } from "react";
import {
    Input,
    InputGroup,
    InputRightElement,
    IconButton,
    Flex,
    Box,
    VStack,
    Button,
    Link
} from "@chakra-ui/react";
import RecipeCards, { Recipe } from "./recipes/RecipeCards";
import "./ProfilePage.css";
import { AuthService } from "./auth/auth-service";
import axios, { all } from "axios";


const ProfilePage = () => {
    const [allRecipes, setAllRecipes] = useState<Recipe[]>([]);

    useEffect(() => {
        axios
            .get(`/users/${AuthService.userName}/recipes`, {
                headers: {
                    Authorization : `${AuthService.authToken}`
                    }
            })
            .then(response => {
                setAllRecipes(response.data);
            })
            .catch(error => {
                console.error('Hiba történt a kérés során!\n', error);
            });
    }, []);

    return (
        <VStack spacing={4}>
            <Box p={5} className="tillana-font">
                <div>
                    <RecipeCards recipes={allRecipes}></RecipeCards>
                </div>
            </Box>
        </VStack>
    );
};


export default ProfilePage