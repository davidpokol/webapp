import React from "react";
import { Input, InputGroup, InputRightElement, IconButton } from "@chakra-ui/react";
import { SearchIcon } from "@chakra-ui/icons";

const Home = () => {
    return (
        
            <InputGroup>
                <Input placeholder="Keresés" />
                <InputRightElement>
                    <IconButton aria-label="Keresés" icon={<SearchIcon />}
                        colorScheme="blue"
                        onClick={() => {
                            // Keresési logika
                        }}
                    />
                </InputRightElement>
            </InputGroup>
        
    )
}

export default Home