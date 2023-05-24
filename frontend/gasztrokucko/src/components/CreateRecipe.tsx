import React, { useState } from 'react';
import { Formik, Field } from 'formik'
import { Box, Button, Checkbox, Flex, FormControl, FormErrorMessage, FormLabel, Input, VStack } from '@chakra-ui/react';

const CreateRecipe = () => {
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
                    onSubmit={(values) => {
                        alert(JSON.stringify(values, null, 2));
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
                                        as={Input}
                                        id="ingredients"
                                        name="ingredients"
                                        type="text"
                                        variant="filled"
                                        validate={(value: string) => {
                                            if (value.length == 0) {
                                                return "A hozzávalók mező nem lehet üres!"
                                            }
                                        }} />
                                    <FormErrorMessage>{errors.ingredients}</FormErrorMessage>
                                </FormControl>
                                <FormControl
                                isInvalid={!!errors.preparation && touched.preparation}>
                                    <FormLabel htmlFor="preparation">Elkészítés</FormLabel>
                                    <Field
                                        as={Input}
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
                                    <FormLabel htmlFor="image">Kép</FormLabel>
                                    <Field
                                        as={Input}
                                        id="image"
                                        name="image"
                                        type="file"
                                        variant="filled" />
                                </FormControl>
                                <Button type="submit" colorScheme="orange" w="full">Recept Hozzáadás</Button>
                            </VStack>
                        </form>
                    )}
                </Formik>
            </Box>
        </Flex>
    )
}

export default CreateRecipe