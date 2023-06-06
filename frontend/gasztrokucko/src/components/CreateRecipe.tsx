import React, { useState, useCallback, useEffect } from 'react';
import axios from 'axios';
import { Formik, Field } from 'formik';
import {
    Box,
    Button,
    Flex,
    FormControl,
    FormErrorMessage,
    FormLabel,
    HStack,
    Input,
    Radio,
    RadioGroup,
    Select,
    Textarea,
    VStack,
    useToast
} from '@chakra-ui/react';
import { useDropzone } from 'react-dropzone';
import moment from 'moment';
import 'moment/locale/hu';
import { AuthService } from './auth/auth-service';
import { Recipe } from './recipes/RecipeCards';
import { useParams } from 'react-router-dom';
import { error } from 'console';

let img: File;

const DragAndDropImage = () => {
    const onDrop = useCallback((acceptedFiles: File[]) => {
        console.log(acceptedFiles[0]);
        img = acceptedFiles[0];
    }, [])
    const { getRootProps, getInputProps, isDragActive } = useDropzone({ onDrop, maxFiles: 1, multiple: false, accept: { "image/*": [".png", ".gif", ".jpeg", ".jpg"] } })

    return (
        <Box {...getRootProps()}
            w={'100%'}
            textAlign={'center'}
            border={'dashed'}
            borderColor={'#F4722B'}
            borderRadius={15}
            p={6}
            rounded={'md'}
            cursor={'pointer'}
        >
            <input {...getInputProps()} />
            {
                isDragActive ?
                    <p>Húzd ide a képet</p> :
                    <p>Húzd be a képet vagy kattints ide a beillesztéshez</p>
            }
        </Box>
    )
}

const CreateRecipe = () => {
    const toast = useToast();

    const [username, setUsername] = useState('');
    useEffect(() => {
        if (AuthService.userName) {
            const name = AuthService.userName;
            setUsername(name);
        }
    }, [])

    const [ingredientsList, setIngredients] = useState('');
    const [ingredients, setIngredientsArray] = useState<string[]>([]);
    const [imageId, setImageId] = useState();

    const { id } = useParams();

    const [recipe, setRecipe] = useState<Recipe>();
    const [isUpdate, setIsUpdate] = useState(false);
    const [difficulty, setDifficulty] = useState<string>();

    useEffect(() => {
        if (id) {
            axios.get(`/recipes/${id}`, {
                headers: {
                    Authorization: `${AuthService.authToken}`
                }
            })
                .then(res => {
                    setRecipe(res.data);
                    setDifficulty(res.data.difficulty);
                    setIsUpdate(true);
                })
                .catch(error => {
                    console.log("Recept módosítása");
                })
        }
    }, [id])


    const handleIngredients = () => {
        setIngredientsArray(ingredientsList.split(/[;\r\n]+/).map((item) => item.trim()));
        console.log(ingredients);
    }


    return (
        <Flex bg="#F6E7C1" align="center" justify="center" h="100vh" color="#F4722B">
            <Box bg="#3E3E3E" p={6} rounded="md" w={420}>
                <Formik
                    enableReinitialize
                    initialValues={{
                        name: recipe !== undefined ? recipe.name : "",
                        createdBy: recipe !== undefined ? recipe.createdBy : "",
                        lastModified: recipe !== undefined ? recipe.lastModified : "",
                        recipeModificationType: recipe !== undefined ? "MODIFIED" : "CREATED",
                        category: recipe !== undefined ? recipe.category : "",
                        difficulty: "",
                        ingredients: recipe !== undefined ? recipe.ingredients : "",
                        instructions: recipe !== undefined ? recipe.instructions : "",
                        photo: ""
                    }}
                    onSubmit={async (values, { resetForm }) => {
                        const postData = {
                            name: values.name,
                            createdBy: username,
                            lastModified: moment().format('YYYY-MM-DD HH:mm:ss'),
                            recipeModificationType: recipe !== undefined ? "MODIFIED" : "CREATED",
                            category: values.category,
                            difficulty: values.difficulty,
                            ingredients,
                            instructions: values.instructions
                        };
                        if (!isUpdate) {
                            const res = await axios.post(`/recipes/add`, postData, {
                                headers: {
                                    Authorization: `${AuthService.authToken}`
                                }
                            })
                                .then(response => {
                                    const imageUpload = async () => {
                                        const imageData = new FormData();
                                        imageData.append("photo", img);
                                        await axios.post(`/recipes/${response.data.id}/image`, imageData, {
                                            headers: {
                                                Authorization: `${AuthService.authToken}`
                                            }
                                        })
                                            .then(response => {
                                                resetForm();
                                                toast({
                                                    title: 'Recept hozzáadása',
                                                    description: "Sikeres recept hozzáadás!",
                                                    status: 'success',
                                                    position: 'top',
                                                    duration: 9000,
                                                    isClosable: true,
                                                });
                                            })
                                            .catch(error => {
                                                toast({
                                                    title: 'Recept hozzáadása',
                                                    description: "Sikertelen recept hozzáadás!",
                                                    status: 'error',
                                                    position: 'top',
                                                    duration: 9000,
                                                    isClosable: true,
                                                });
                                            })
                                    }
                                    imageUpload();
                                })
                                .catch(error => {
                                    toast({
                                        title: 'Recept hozzáadása',
                                        description: "Hiba történt a kérés során!",
                                        status: 'error',
                                        position: 'top',
                                        duration: 9000,
                                        isClosable: true,
                                    });
                                })
                        } else {
                            const id = recipe !== undefined ? recipe.id : null;
                            const updatedPostData = {
                                ...postData,
                                id: id
                            };
                            axios.put(`/recipes/update`, updatedPostData, {
                                headers: {
                                    Authorization: `${AuthService.authToken}`
                                }
                            })
                            .then(res => {
                                toast({
                                    title: 'Recept frissítése',
                                    description: "Sikeres frissítés!",
                                    status: 'success',
                                    position: 'top',
                                    duration: 9000,
                                    isClosable: true,
                                });
                            })
                            .catch(error => {
                                toast({
                                    title: 'Recept frissítése',
                                    description: "Sikertelen frissítés!",
                                    status: 'error',
                                    position: 'top',
                                    duration: 9000,
                                    isClosable: true,
                                });
                            })
                        }
                    }}
                >
                    {({ handleSubmit, errors, touched }) => (
                        <form onSubmit={handleSubmit}>
                            <VStack spacing={4} align="flex-start">
                                <FormControl
                                    isInvalid={!!errors.name && touched.name}>
                                    <FormLabel htmlFor="name">Elnevezés</FormLabel>
                                    <Field
                                        as={Input}
                                        id="name"
                                        name="name"
                                        type="text"
                                        variant="filled"
                                        validate={(value: string) => {
                                            if (value.length == 0) {
                                                return "Az elnevezés mező nem lehet üres!"
                                            }
                                            else if (value.length < 5) {
                                                return "Nem lehet rövidebb 5 karakternél!"
                                            }
                                        }} />
                                    <FormErrorMessage>{errors.name}</FormErrorMessage>
                                </FormControl>
                                <FormControl
                                    isInvalid={!!errors.category && touched.category}>
                                    <FormLabel htmlFor="category">Kategória</FormLabel>
                                    <Field
                                        as={Select}
                                        id="category"
                                        name="category"
                                        variant="filled"
                                        validate={(value: string) => {
                                            if (value === "") {
                                                return "Válassz ki egy kategóriát!"
                                            }
                                        }}>
                                        <option value="" disabled>Válassz</option>
                                        <option value={"BREAKFAST"}>Reggeli</option>
                                        <option value={"BRUNCH"}>Villásreggeli</option>
                                        <option value={"ELEVENSES"}>Tízórai</option>
                                        <option value={"LUNCH"}>Ebéd</option>
                                        <option value={"TEA"}>Tea</option>
                                        <option value={"SUPPER"}>Uzsonna</option>
                                        <option value={"DINNER"}>Vacsora</option>
                                    </Field>
                                    <FormErrorMessage>{errors.category}</FormErrorMessage>
                                </FormControl>
                                <FormControl
                                    isInvalid={!!errors.difficulty && touched.difficulty}>
                                    <FormLabel htmlFor="difficulty">Nehézség</FormLabel>
                                    <RadioGroup name="difficulty" defaultValue={difficulty}>
                                        <HStack spacing={4}>
                                            <Field as={Radio} value="EASY">Könnyű</Field>
                                            <Field as={Radio} value="MODERATE">Haladó</Field>
                                            <Field as={Radio} value="HARD">Nehéz</Field>
                                        </HStack>
                                    </RadioGroup>
                                    <FormErrorMessage>{errors.difficulty}</FormErrorMessage>
                                </FormControl>
                                <FormControl
                                    isInvalid={!!errors.ingredients && touched.ingredients}>
                                    <FormLabel htmlFor="ingredients">Hozzávalók</FormLabel>
                                    <Field
                                        as={Textarea}
                                        id="ingredients"
                                        name="ingredients"
                                        type="text"
                                        variant="filled"
                                        validate={(value: string) => {
                                            if (value.length == 0) {
                                                return "A hozzávalók mező nem lehet üres!"
                                            }
                                            else {
                                                setIngredients(value);
                                            }
                                        }} />
                                    <FormErrorMessage>{errors.ingredients}</FormErrorMessage>
                                </FormControl>
                                <FormControl
                                    isInvalid={!!errors.instructions && touched.instructions}>
                                    <FormLabel htmlFor="instructions">Elkészítés</FormLabel>
                                    <Field
                                        as={Textarea}
                                        id="instructions"
                                        name="instructions"
                                        type="text"
                                        variant="filled"
                                        validate={(value: string) => {
                                            if (value.length == 0) {
                                                return "Az elkészítés mező nem lehet üres!"
                                            }
                                        }} />
                                    <FormErrorMessage>{errors.ingredients}</FormErrorMessage>
                                </FormControl>
                                <FormControl>
                                    <FormLabel>Kép</FormLabel>
                                    <DragAndDropImage />
                                </FormControl>
                                <Button type="submit" onClick={handleIngredients} colorScheme="orange" w="full">Recept Hozzáadás</Button>
                            </VStack>
                        </form>
                    )}
                </Formik>
            </Box>
        </Flex>
    )
}

export default CreateRecipe