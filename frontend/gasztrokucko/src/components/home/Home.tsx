import React from "react";
import {
    Input,
    InputGroup,
    InputRightElement,
    IconButton,
    Flex,
    Select,
    Box,
    VStack
} from "@chakra-ui/react";
import { SearchIcon } from "@chakra-ui/icons";
import "./Home.css";
import RecipeCards from "../recipes/RecipeCards";


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

const Home = () => {
    return (
        <VStack spacing={4}>
            <Box className="tillana-font">
                <Flex bg="#F6E7C1" justify="center" p={10}>
                    <Select placeholder="Kategóriák" border="solid 1px #B3A78C" borderRadius={15} w="20vh" marginRight={3}>
                        <option>Leves</option>
                        <option>Főétél</option>
                        <option>Desszert</option>
                    </Select>
                    <InputGroup w="75vh">
                        <Input placeholder="Keresés" bg="#B3A78C" color="#626262" borderRadius={15} />
                        <InputRightElement>
                            <IconButton aria-label="Keresés" bg="#B3A78C" borderRadius={15} size="sm" icon={<SearchIcon />}
                                colorScheme="blue"
                                onClick={() => {
                                    // Keresési logika
                                }}
                            />
                        </InputRightElement>
                    </InputGroup>
                </Flex>
            </Box>
            <Box p={5} className="tillana-font">
                <div><RecipeCards recipes={recipes}></RecipeCards></div>
            </Box>
        </VStack >
    )
}

export default Home