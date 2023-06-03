import React, { useState, useCallback } from 'react';
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
    VStack
} from '@chakra-ui/react';
import { useDropzone } from 'react-dropzone';
import moment from 'moment';
import 'moment/locale/hu';

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

    const [ingredientsList, setIngredients] = useState('');
    const [ingredients, setIngredientsArray] = useState<string[]>([]);

    const handleIngredients = () => {
        setIngredientsArray(ingredientsList.split(/[;\r\n]+/).map((item) => item.trim()));
        console.log(ingredients);
    }

    return (
        <Flex bg="#F6E7C1" align="center" justify="center" h="100vh" color="#F4722B">
            <Box bg="#3E3E3E" p={6} rounded="md" w={420}>
                <Formik
                    initialValues={{
                        name: "",
                        createdBy: "",
                        lastModified: "",
                        recipeModificationType: "",
                        category: "",
                        difficulty: "",
                        ingredients: "",
                        instructions: "",
                        photo: ""
                    }}
                    onSubmit={ async (values, { resetForm }) => {
                        const postData = {
                            name: values.name,
                            createdBy: "felhasználó",
                            lastModified: moment().format('YYYY-MM-DD HH:mm:ss'),
                            recipeModificationType: "CREATED",
                            category: values.category,
                            difficulty: values.difficulty,
                            ingredients,
                            instructions: values.instructions,
                            photo: "logo192.png"
                        };
                        //resetForm();
                        alert(JSON.stringify(postData, null, 2));
                        const res = await axios.post(`/recipes/add`, postData)
                            /*.then(response => {

                            })
                            .catch(error => {
                                console.error('Hiba történt a kérés során!\n', error);
                            })*/
                            if (res.status !== 201) {
                                alert(res.status);
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
                                    <FormLabel htmlFor="difficulty">Kategória</FormLabel>
                                    <RadioGroup name="difficulty">
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