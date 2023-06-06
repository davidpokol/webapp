import React, { useEffect, useState } from "react"
import { Flex, Box, HStack, UnorderedList, ListItem, Heading, Text, List } from "@chakra-ui/layout"
import { Button, useToast } from "@chakra-ui/react"
import { Image } from "@chakra-ui/image"
import { useParams, Link } from "react-router-dom"
import axios from "axios"
import "./RecipePage.css";
import moment from 'moment';
import 'moment/locale/hu';
import { AuthService } from "../auth/auth-service"
import { Recipe } from "./RecipeCards"

const RecipePage = () => {

  const toast = useToast();

  const [name, setName] = useState();
  const [createdBy, setCreatedBy] = useState();
  const [lastModified, setLastModified] = useState<string>();
  const [difficulty, setDifficulty] = useState<string>();
  const [category, setCategory] = useState<string>();
  const [categoryEn, setCategoryEn] = useState<string>();
  const [ingredients, setIngredients] = useState([]);
  const [instructions, setInstructions] = useState();
  const [photo, setPhoto] = useState<string>();
  const [recipeId, setRecipeId] = useState<string>();

  const [allRecipes, setAllRecipes] = useState<Recipe[]>([]);

  const filterRecipesByCategory = (category: string) => {
    const filteredRecipes = allRecipes.filter(recipe => recipe.category === category);
    return filteredRecipes;
  };

  const { id } = useParams();
  useEffect(() => {
    axios
      .get(`/recipes/${id}`, {
        headers: {
          Authorization: `${AuthService.authToken}`
        }
      })
      .then((res) => {
        setName(res.data.name);
        setRecipeId(res.data.id);
        switch (res.data.difficulty) {
          case "EASY":
            setDifficulty("Könnyű");
            break;
          case "MODERATE":
            setDifficulty("Haladó");
            break;
          case "HARD":
            setDifficulty("Nehéz");
            break;
        }

        setIngredients(res.data.ingredients);
        setInstructions(res.data.instructions);
        switch (res.data.category) {
          case "BREAKFAST":
            setCategory("Reggeli");
            break;
          case "BRUNCH":
            setCategory("Villásreggeli");
            break;
          case "ELEVENSES":
            setCategory("Tízórai");
            break;
          case "LUNCH":
            setCategory("Ebéd");
            break;
          case "TEA":
            setCategory("Tea");
            break;
          case "SUPPER":
            setCategory("Uzsonna");
            break;
          case "DINNER":
            setCategory("Vacsora");
            break;
        }
        setCategoryEn(res.data.category)
        setCreatedBy(res.data.createdBy)
        setLastModified(moment(res.data.lastModified, 'YYYY-MM-DD HH:mm:ss').fromNow())
        axios
          .get(`/recipes/${id}/image`, {
            responseType: "blob",
            headers: {
              Authorization: `${AuthService.authToken}`
            }
          })
          .then((res) => {
            const blob = new Blob([res.data], { type: "image/png" });
            const imageUrl = URL.createObjectURL(blob);
            setPhoto(imageUrl);
          })
          .catch((error) => {
            toast({
              title: "Kép",
              description: "Nem sikerült a képet betölteni!",
              status: "error",
              position: "top",
              duration: 9000,
              isClosable: true,
            });
          });
      })
      .catch((error) => {
        toast({
          title: "Hiba",
          description: "Valami hiba csúszott a gépezetbe!",
          status: "error",
          position: "top",
          duration: 9000,
          isClosable: true,
        });
      });
    axios.get('/recipes', {
      headers: {
        Authorization: `${AuthService.authToken}`
      }
    })
      .then(response => {
        console.log(response.data);
        setAllRecipes(response.data)
      })
      .catch(error => {
        console.error('Hiba történt a kérés során!\n', error);
      })
  }, [id]);

  const handleDelete = (receptId: string | undefined) => {
    axios.delete(`/recipes/${receptId}`, {
      headers: {
        Authorization: `${AuthService.authToken}`
      }
    })
      .then(response => {
        window.location.href = "/"
        toast({
          title: 'Törlés',
          description: "Sikeres törlés!",
          status: 'success',
          position: 'top',
          duration: 9000,
          isClosable: true,
        });
      })
      .catch(error => {
        error.response.status === 403 ?
          toast({
            title: 'Törlés',
            description: "Nem saját recept!",
            status: 'error',
            position: 'top',
            duration: 9000,
            isClosable: true,
          }) :
          toast({
            title: 'Törlés',
            description: "Sikertelen törlés!",
            status: 'error',
            position: 'top',
            duration: 9000,
            isClosable: true,
          })
      })
  }

  return (
    <Flex height={"100vh"} fontFamily={"Kalam"}>
      <Box height={"100%"} width={"75%"} padding={10} style={{ textAlign: 'left' }} border={"5px solid #F4722B"} margin={5} borderRadius={25}>
        <Image src={photo} float="right" mr={5} boxSize={200}></Image>
        <Text>Nehezség: {difficulty} </Text>
        <Text>Kategória: {category} </Text>
        <Heading as={"h1"} mt={2}>{name}</Heading>
        <Text>Készítette: {createdBy}</Text>
        <Text>Utolsó módosítás: {lastModified} </Text>
        <Text mt={5}>Hozzávalók:</Text>
        <UnorderedList mt={2}>
          {ingredients.map((ingredient: string, index: number) => (
            <ListItem key={index}>{ingredient}</ListItem>
          ))}
        </UnorderedList>
        <Text mt={5}>Elkészítés:</Text>
        <Text mt={2}>{instructions}</Text>
        <Button as={Link} to={`/recipe/${recipeId}/update`} margin={2} borderRadius={15} backgroundColor={"#F4722B"}>Recept Módosítás</Button>
        <Button onClick={() => handleDelete(recipeId)} margin={2} borderRadius={15} backgroundColor={"#F4722B"}>Recept Törlés</Button>
      </Box>
      <Box width={"25%"} padding={10} border={"5px solid #F4722B"} margin={5} borderRadius={25}>
        <Heading fontFamily={"Kalam"} as={"h1"}>Hasonló receptek: </Heading>
        {categoryEn ?
          <List mt={5} flexDirection={"column"} display={"flex"}>
            {filterRecipesByCategory(categoryEn).map((recipe, index) => (
              <ListItem to={`/recipe/${recipe.id}`} as={Link} key={index}>{recipe.name}</ListItem>
            ))}
          </List> : (null)
        }
      </Box>
    </Flex>
  )
}

export default RecipePage