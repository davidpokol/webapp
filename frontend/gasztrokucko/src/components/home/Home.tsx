import React, { useEffect, useReducer, useState } from "react";
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
import RecipeCards, { Recipe } from "../recipes/RecipeCards";
import axios from "axios";

const Home = () => {
    const [allRecipes, setAllRecipes] = useState<Recipe[]>([]);
    const [breakfasts, setBreakfasts] = useState<Recipe[]>([]);
    const [brunch, setBrunch] = useState<Recipe[]>([]);
    const [elevenses, setElevenses] = useState<Recipe[]>([]);
    const [lunches, setLunches] = useState<Recipe[]>([]);
    const [teas, setTeas] = useState<Recipe[]>([]);
    const [suppers, setSuppers] = useState<Recipe[]>([]);
    const [dinners, setDinners] = useState<Recipe[]>([]);

    useEffect(() => {
        const res = async () => {
            await axios.get('/recipes')
                .then(response => {
                    console.log(response.data);
                    setAllRecipes(response.data)
                    setBreakfasts(allRecipes.filter((recipe) => recipe.category === "BREAKFAST"))
                    setBrunch(allRecipes.filter((recipe) => recipe.category === "BRUNCH"))
                    setElevenses(allRecipes.filter((recipe) => recipe.category === "ELEVENSES"))
                    setLunches(allRecipes.filter((recipe) => recipe.category === "LUNCH"))
                    setTeas(allRecipes.filter((recipe) => recipe.category === "TEA"))
                    setSuppers(allRecipes.filter((recipe) => recipe.category === "SUPPER"))
                    setDinners(allRecipes.filter((recipe) => recipe.category === "DINNER"))
                    console.log("Teák:" + teas);
                })
                .catch(error => {
                    console.error('Hiba történt a kérés során!\n', error);
                })
        }
        res();
    }, [])

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
                                    // TODO: Keresési logika
                                }}
                            />
                        </InputRightElement>
                    </InputGroup>
                </Flex>
            </Box>
            <Box p={5} className="tillana-font">
                <div><RecipeCards recipes={allRecipes}></RecipeCards></div>
            </Box>
        </VStack >
    )
}

export default Home