import React from 'react';
import { Formik, Field } from 'formik'
import { Box, Button, Checkbox, Flex, FormControl, FormErrorMessage, FormLabel, Input, VStack } from '@chakra-ui/react';

const Login = () => {
    return (
        <Flex bg="#F6E7C1" align="center" justify="center" h="100vh" color="#F4722B">
            <Box bg="#3E3E3E" p={6} rounded="md" w={420}>
                <Formik
                    initialValues={{
                        email: "",
                        password: "",
                        rememberMe: false
                    }}
                    onSubmit={(values) => {
                        alert(JSON.stringify(values, null, 2));
                    }}
                >
                    {({ handleSubmit, errors, touched }) => (
                        <form onSubmit={handleSubmit}>
                            <VStack spacing={4} align="flex-start">
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