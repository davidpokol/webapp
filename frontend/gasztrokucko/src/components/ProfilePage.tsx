import React from "react";
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
import { SearchIcon } from "@chakra-ui/icons";
import RecipeCards from "./recipes/RecipeCards";
import "./ProfilePage.css";

const recipes = [
    {
        name: "Chocolate Cake",
        image: "logo192.png"
    },
    {
        name: "Pasta Carbonara",
        image: "logo192.png"
    },
    {
        name: "Pasta Carbonara",
        image: "logo192.png"
    },
    {
        name: "Chocolate Cake",
        image: "logo192.png"
    },
    {
        name: "Chocolate Cake",
        image: "logo192.png"
    },
];
const ProfilePage = () => {
    return (
        <VStack spacing={4}>
            <Box className="tillana-font">
                <Flex bg="#F6E7C1" justify="center" p={10}>
                    <Button as={Link} borderRadius={15} w="20vh" marginRight={3} bg="#B3A78C">Saját</Button>
                    <Button as={Link} borderRadius={15} w="20vh"  bg="#B3A78C">Kedvencek</Button>
                </Flex>
            </Box>
            <Box p={5} className="tillana-font">
                <div><RecipeCards recipes={recipes}></RecipeCards></div>
            </Box>
        </VStack >
    )
}

export default ProfilePage