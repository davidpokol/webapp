import React from "react";
import { 
    Input,
    InputGroup,
    InputRightElement,
    IconButton,
    Flex,
    Select
 } from "@chakra-ui/react";
import { SearchIcon } from "@chakra-ui/icons";
import "./Home.css";

const Home = () => {
    return (
        <Flex bg="#F6E7C1" h="100vh" justify="center" p={10}>
            <Select placeholder="Kategóriák" border="solid 1px #B3A78C" borderRadius={15} w="20vh" pr={3}>
                <option>Leves</option>
                <option>Főétél</option>
                <option>Desszert</option>
            </Select>
            <InputGroup w="75vh">
                <Input placeholder="Keresés" bg="#B3A78C" color="#626262" borderRadius={15} />
                <InputRightElement>
                    <IconButton aria-label="Keresés" bg="#B3A78C" borderRadius={15} size="sm" icon={<SearchIcon />}
                        colorScheme="blue"
                        onClick={() => {
                            // Keresési logika
                        }}
                    />
                </InputRightElement>
            </InputGroup>
        </Flex>
    )
}

export default Home