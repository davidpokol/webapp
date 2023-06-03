import React from 'react';
import { Box, Flex, Text, VStack, Image } from '@chakra-ui/react';

export interface Recipe {
  name: string;
  createdBy: string,
  lastModified: string,
  recipeModificationType: string,
  category: string,
  difficulty: string,
  ingredients: string[],
  instructions: string,
  photo: string,
  favoritedBy: string[]
}

interface RecipeCardsProps {
  recipes: Recipe[];
}

const RecipeCards: React.FC<RecipeCardsProps> = ({ recipes }: RecipeCardsProps) => {
  return (
    <Flex flexWrap="wrap">
      {recipes.map((recipe: Recipe, index: number) => (
        <Box key={index} width="25%" padding="1rem">
          <Box borderWidth="1px" borderRadius="lg" overflow="hidden" boxShadow="md" padding="1rem" bg="#B3A78C">
            <Flex justifyContent="center" alignItems="center" height="200px">
              <Image src={recipe.photo} alt={recipe.name} mb="0.5rem" />
            </Flex>
            <Text fontSize="xl" fontWeight="bold" textAlign="center" mb="0.5rem">
              {recipe.name}
            </Text>
          </Box>
        </Box>
      ))}
    </Flex>
  );
};

export default RecipeCards;