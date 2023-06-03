import React, { useEffect, useState } from 'react';
import { Formik, Field, useFormik } from 'formik'
import { Box, Button, Checkbox, Flex, FormControl, FormErrorMessage, FormLabel, Input, VStack, useToast } from '@chakra-ui/react';
import { AuthService } from './auth-service';
import 'react-toastify/dist/ReactToastify.css';
import { AxiosError } from 'axios';

const Login = () => {
    const toast = useToast();
    const [username, setUsername] = useState('');
    const [password, setPassowrd] = useState('');
    const [rememberMe, setRememberMe] = useState(false);

    useEffect(() => {
        if (AuthService.userDetails) {
            const data = AuthService.userDetails?.split(';');
            setUsername(data[0]);
            setPassowrd(data[1]);
            setRememberMe(data[2] === "true");
        }
    }, []);

    // TODO: Kijavítani a Formik input által kapott értékeit

    return (
        <Flex bg="#F6E7C1" align="center" justify="center" h="100vh" color="#F4722B">
            <Box bg="#3E3E3E" p={6} rounded="md" w={420}>
                <Formik
                    initialValues={{
                        username: username,
                        password: password,
                        rememberMe: rememberMe
                    }}
                    onSubmit={async (values) => {
                        try {
                            console.log(values.username + " | " + values.password + " | " + values.rememberMe);
                            await AuthService.login(values.username, values.password, values.rememberMe);
                            window.location.href = "/"
                        } catch (e: any) {
                            if (e.response.status === 401) {
                                toast({
                                    title: 'Regisztráció',
                                    description: "Helytelen felhasználónév-jelszó páros!",
                                    status: 'error',
                                    position: 'top',
                                    duration: 9000,
                                    isClosable: true,
                                });
                            } else if (e.response.status !== 200) {
                                toast({
                                    title: 'Regisztráció',
                                    description: "Hoppá! Valami nagy a baj! Próbáld meg újra!",
                                    status: 'error',
                                    position: 'top',
                                    duration: 9000,
                                    isClosable: true,
                                });
                            }
                        }
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
                                        type="username"
                                        variant="filled"
                                        validate={(value: string) => {
                                            if (value.length == 0) {
                                                return "A felhasználónév mező nem lehet üres!"
                                            }
                                        }} />
                                    <FormErrorMessage>{errors.username}</FormErrorMessage>
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
                                            if (value.length == 0) {
                                                return "A jelszó mező nem lehet üres!"
                                            }
                                        }} />
                                    <FormErrorMessage>{errors.password}</FormErrorMessage>
                                </FormControl>
                                <Field as={Checkbox} id="rememberMe" name="rememberMe" colorScheme="orange">Emlékezz rám</Field>
                                <Button type="submit" colorScheme="orange" w="full">Bejelentkezés</Button>
                            </VStack>
                        </form>
                    )}
                </Formik>
            </Box>
        </Flex>
    )
}

export default Login