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
    Input,
    Textarea,
    VStack
} from '@chakra-ui/react';
import { useDropzone } from 'react-dropzone';

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

    const [ingredients, setIngredients] = useState('');
    const [ingredientsArray, setIngredientsArray] = useState<string[]>([]);

    const handleIngredients = () => {
        setIngredientsArray(ingredients.split(/[;\r\n]+/).map((item) => item.trim()));
        console.log(ingredientsArray);
    }

    return (
        <Flex bg="#F6E7C1" align="center" justify="center" h="100vh" color="#F4722B">
            <Box bg="#3E3E3E" p={6} rounded="md" w={420}>
                <Formik
                    initialValues={{
                        name: "",
                        ingredients: "",
                        preparation: "",
                        image: ""
                    }}
                    onSubmit={(values, { resetForm }) => {
                        const postData = {
                            name: values.name,
                            ingredientsArray,
                            preparation: values.preparation,
                            image: img
                        };
                        resetForm();
                        alert(JSON.stringify(postData, null, 2));
                        axios.post('http://localhost:5000/recipes/add', postData)
                            .then(response => {

                            })
                            .catch(error => {
                                console.error('Hiba történt a kérés során!\n', error);
                            })
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
                                    isInvalid={!!errors.preparation && touched.preparation}>
                                    <FormLabel htmlFor="preparation">Elkészítés</FormLabel>
                                    <Field
                                        as={Textarea}
                                        id="preparation"
                                        name="preparation"
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