import React from "react";
import { Button, Box } from "@chakra-ui/react";
import { NavLink, Link } from "react-router-dom";
import { Image, Flex, HStack, chakra, Select } from "@chakra-ui/react";

const Header = () => {
    const Header = chakra("header");
    return (
        <Header className="my-header">
            <Flex w="100%" px="6" py="5" align="center" justify="space-between">

                <Box as={Link} to="/">
                    <Image src="logo192.png" h="50px" />
                </Box>

                <HStack>
                    <Select placeholder="Kategóriák">
                        <option>Leves</option>
                        <option>Főétél</option>
                        <option>Desszert</option>
                    </Select>
                </HStack>

                <HStack spacing={4}>
                    <Link to="/login">Bejelentkezés</Link>
                    <Link to="/registration">Regisztráció</Link>
                </HStack>

            </Flex>
        </Header>
    );
}

export default Header