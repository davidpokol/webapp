import React, { useState } from 'react';
import axios from 'axios';
import { Formik, Field } from 'formik'
import { Box, Button, Checkbox, Flex, FormControl, FormErrorMessage, FormLabel, Input, VStack, useToast } from '@chakra-ui/react';
import { AuthService } from './auth-service';

const Registration = () => {
    const toast = useToast();
    const [password, setPassowrd] = useState("");
    return (
        <Flex bg="#F6E7C1" align="center" justify="center" h="100vh" color="#F4722B">
            <Box bg="#3E3E3E" p={6} rounded="md" w={420}>
                <Formik
                    initialValues={{
                        username: "",
                        email: "",
                        password: "",
                        passwordMatch: ""
                    }}
                    onSubmit={async (values, { resetForm }) => {
                        try {
                            await AuthService.registration(values.username, values.email, values.password, toast);
                        } catch (e: any) {
                            if (e.response.status === 409) {
                                toast({
                                    title: 'Regisztráció',
                                    description: "A felhasználó már létezik!",
                                    status: 'error',
                                    position: 'top',
                                    duration: 9000,
                                    isClosable: true,
                                });
                            } else if (e.response.status !== 201) {
                                toast({
                                    title: 'Regisztráció',
                                    description: "Valami hiba csúszott a gépezetbe!",
                                    status: 'error',
                                    position: 'top',
                                    duration: 9000,
                                    isClosable: true,
                                });
                            }
                        }
                        resetForm();
                    }}
                >
                    {({ handleSubmit, errors, touched }) => (
                        <form onSubmit={handleSubmit}>
                            <VStack spacing={4} align="flex-start">
                                <FormControl
                                    isInvalid={!!errors.username && touched.username}>
                                    <FormLabel htmlFor="username">Felhasználónév</FormLabel>
                                    <Field
                                        as={Input}
                                        id="username"
                                        name="username"
                                        type="text"
                                        variant="filled"
                                        validate={(value: string) => {
                                            if (value.length < 5) {
                                                return "A felhasználónévnek hosszabnak kell lennie 5 karakternél!"
                                            }
                                        }} />
                                    <FormErrorMessage>{errors.username}</FormErrorMessage>
                                </FormControl>
                                <FormControl>
                                    <FormLabel htmlFor="email">Email</FormLabel>
                                    <Field
                                        as={Input}
                                        id="email"
                                        name="email"
                                        type="email"
                                        variant="filled" />
                                </FormControl>
                                <FormControl
                                    isInvalid={!!errors.password && touched.password}>
                                    <FormLabel htmlFor="password">Jelszó</FormLabel>
                                    <Field
                                        as={Input}
                                        id="password"
                                        name="password"
                                        type="password"
                                        variant="filled"
                                        validate={(value: string) => {
                                            if (value.length <= 5) {
                                                return "A jelszónak hosszabnak kell lennie 5 karakternél!"
                                            }
                                            else {
                                                setPassowrd(value);
                                            }
                                        }} />
                                    <FormErrorMessage>{errors.password}</FormErrorMessage>
                                </FormControl>
                                <FormControl
                                    isInvalid={!!errors.passwordMatch && touched.passwordMatch}>
                                    <FormLabel htmlFor="passwordMatch">Jelszó újra</FormLabel>
                                    <Field
                                        as={Input}
                                        id="passwordMatch"
                                        name="passwordMatch"
                                        type="password"
                                        variant="filled"
                                        validate={(value: string) => {
                                            if (value.length <= 5) {
                                                return "A jelszónak hosszabnak kell lennie 5 karakternél!"
                                            }
                                            if (value !== password) {
                                                return "A jelszavak nem egyeznek!"
                                            }
                                        }}
                                    />
                                    <FormErrorMessage>{errors.passwordMatch}</FormErrorMessage>
                                </FormControl>
                                <Button type="submit" colorScheme="orange" w="full">Regisztráció</Button>
                            </VStack>
                        </form>
                    )}
                </Formik>
            </Box>
        </Flex>
    )
}

export default Registration