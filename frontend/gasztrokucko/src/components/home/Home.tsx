import React, { ChangeEvent, useEffect, useReducer, useState } from "react";
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

    const [selectedCategory, setSelectedCategory] = useState('');

    const [searchedRecipes, setSearchedRecipes] = useState<Recipe[]>([]);

    const filterRecipesByCategory = (category: string) => {
        const filteredRecipes = allRecipes.filter(recipe => recipe.category === category);
        return filteredRecipes;
    };

    const handleCategorySelect = (event: ChangeEvent<HTMLSelectElement>) => {
        setSelectedCategory(event.target.value);
    };

    const handleSearch = (event: ChangeEvent<HTMLInputElement>, category: string) => {
        const searchText = event.target.value.toLowerCase();
        console.log("Kategória: " + category)
        if (category !== null) {
            setSearchedRecipes(allRecipes.filter(recipe => recipe.name.toLowerCase().includes(searchText)));
            const asd = allRecipes.filter(recipe => recipe.name.toLowerCase().includes(searchText));
            console.log(asd);
        } else {
            const searchCategory = allRecipes.filter(recipe => recipe.category === category);
            setSearchedRecipes(searchCategory.filter(recipe => recipe.name.toLowerCase().includes(searchText)));
            const asd = searchCategory.filter(recipe => recipe.name.toLowerCase().includes(searchText));
            console.log(asd);
        }

    };

    useEffect(() => {
        const res = async () => {
            await axios.get('/recipes')
                .then(response => {
                    console.log(response.data);
                    setAllRecipes(response.data)
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
                    <Select onChange={handleCategorySelect} placeholder="Kategóriák" border="solid 1px #B3A78C" borderRadius={15} w="20vh" marginRight={3}>
                        <option value={"BREAKFAST"}>Reggeli</option>
                        <option value={"BRUNCH"}>Villásreggeli</option>
                        <option value={"ELEVENSES"}>Tízórai</option>
                        <option value={"LUNCH"}>Ebéd</option>
                        <option value={"TEA"}>Tea</option>
                        <option value={"SUPPER"}>Uzsonna</option>
                        <option value={"DINNER"}>Vacsora</option>
                    </Select>
                    <InputGroup w="75vh">
                        <Input onChange={event => handleSearch(event, selectedCategory)} placeholder="Keresés" bg="#B3A78C" color="#626262" borderRadius={15} />
                        <InputRightElement>
                            <IconButton aria-label="Keresés" bg="#B3A78C" borderRadius={15} size="sm" icon={<SearchIcon />}
                                colorScheme="blue"
                            />
                        </InputRightElement>
                    </InputGroup>
                </Flex>
            </Box>
            <Box p={5} className="tillana-font">
                {
                    searchedRecipes.length !== 0 ? (             
                            <div>
                                <RecipeCards recipes={searchedRecipes}></RecipeCards>
                            </div> 
                    ) : (
                        selectedCategory ? (
                            <div>
                                <RecipeCards recipes={filterRecipesByCategory(selectedCategory)}></RecipeCards>
                            </div>
                        ) : (
                            <div>
                                <RecipeCards recipes={allRecipes}></RecipeCards>
                            </div>
                        )
                    )
                }
            </Box>
        </VStack >
    )
}

export default Home