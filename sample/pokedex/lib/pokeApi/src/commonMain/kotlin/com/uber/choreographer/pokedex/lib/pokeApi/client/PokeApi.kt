package com.uber.choreographer.pokedex.lib.pokeApi.client


// Main PokeApiClient interface
interface PokeApi {
    val berryClient: BerryClient
    val contestClient: ContestClient
    val encounterClient: EncounterClient
    val evolutionClient: EvolutionClient
    val gameClient: GameClient
    val itemClient: ItemClient
    val locationClient: LocationClient
    val machineClient: MachineClient
    val moveClient: MoveClient
    val pokemonClient: PokemonClient
    val utilityClient: UtilityClient
}

