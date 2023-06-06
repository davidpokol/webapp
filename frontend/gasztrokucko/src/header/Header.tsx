import React, { useState, useEffect } from "react";
import { Button, Box } from "@chakra-ui/react";
import { NavLink, Link } from "react-router-dom";
import { Image, Flex, HStack, Select } from "@chakra-ui/react";
import { Avatar, AvatarBadge, AvatarGroup, Menu, MenuItem, MenuList, MenuButton } from '@chakra-ui/react'
import { ChevronDownIcon } from "@chakra-ui/icons";
import "./Header.css"
import { AuthService } from "../components/auth/auth-service";

const Header = () => {
    const [isLoggedIn, setIsLoggedIn] = useState<Boolean>();
    const [userToken, setUserToken] = useState<string | null>();
    const [username, setUsername] = useState<string | null>();

    useEffect(() => {
        document.title = "Gasztrokuckó";
        setUserToken(AuthService.authToken);
    }, []);

    useEffect(() => {
        setUsername(AuthService.userName);
    }, [userToken]);

    const handleLogout = () => {
        AuthService.logout();
        window.location.href = "/";
    }

    if (userToken) {
        return (
            <Box as="header" bg="#3E3E3E" color="#F4722B" className="tillana-font">
                <Flex w="100%" px="5" py="3" align="center" justify="space-between">

                    <Box as={Link} to="/">
                        <Image src="gasztrokucko_logo.png" h="50px" />
                    </Box>

                    <Menu>
                        <MenuButton as={Avatar} size="md" src="avatar.png" cursor="pointer" />
                        <MenuList>
                            <MenuItem as={Link} to={`/${username}`}>Profil</MenuItem>
                            <MenuItem as={Link} to={"/create"}>Recept hozzáadása</MenuItem>
                            <MenuItem onClick={handleLogout}>Kijelentkezés</MenuItem>
                        </MenuList>
                    </Menu>

                </Flex>
            </Box>
        )
    } else {
        return (
            <Box as="header" bg="#3E3E3E" color="#F4722B">
                <Flex w="100%" px="5" py="3" align="center" justify="space-between">

                    <Box as={Link} to="/">
                        <Image src="gasztrokucko_logo.png" h="50px" />
                    </Box>

                    <HStack spacing={4}>
                        <Link to="/login">Bejelentkezés</Link>
                        <Link to="/registration">Regisztráció</Link>
                    </HStack>

                </Flex>
            </Box>
        );
    }
}

export default Header