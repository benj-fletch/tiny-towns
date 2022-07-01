package com.benjfletch.tinytowns

import com.benjfletch.tinytowns.model.ALL
import com.benjfletch.tinytowns.model.BRICK
import com.benjfletch.tinytowns.model.EmptySpace
import com.benjfletch.tinytowns.model.GLASS
import com.benjfletch.tinytowns.model.GamePiece
import com.benjfletch.tinytowns.model.NONE
import com.benjfletch.tinytowns.model.Resource
import com.benjfletch.tinytowns.model.STONE
import com.benjfletch.tinytowns.model.WHEAT
import com.benjfletch.tinytowns.model.WOOD
import com.benjfletch.tinytowns.model.buildings.Abbey
import com.benjfletch.tinytowns.model.buildings.Almshouse
import com.benjfletch.tinytowns.model.buildings.Attraction
import com.benjfletch.tinytowns.model.buildings.Bakery
import com.benjfletch.tinytowns.model.buildings.Bank
import com.benjfletch.tinytowns.model.buildings.Building
import com.benjfletch.tinytowns.model.buildings.Chapel
import com.benjfletch.tinytowns.model.buildings.Cloister
import com.benjfletch.tinytowns.model.buildings.Cottage
import com.benjfletch.tinytowns.model.buildings.Factory
import com.benjfletch.tinytowns.model.buildings.Farm
import com.benjfletch.tinytowns.model.buildings.FeastHall
import com.benjfletch.tinytowns.model.buildings.FedCottage
import com.benjfletch.tinytowns.model.buildings.FoodProducer
import com.benjfletch.tinytowns.model.buildings.Fountain
import com.benjfletch.tinytowns.model.buildings.GoodsHandler
import com.benjfletch.tinytowns.model.buildings.Granary
import com.benjfletch.tinytowns.model.buildings.Greenhouse
import com.benjfletch.tinytowns.model.buildings.Inn
import com.benjfletch.tinytowns.model.buildings.Market
import com.benjfletch.tinytowns.model.buildings.Millstone
import com.benjfletch.tinytowns.model.buildings.Orchard
import com.benjfletch.tinytowns.model.buildings.PlaceOfWorship
import com.benjfletch.tinytowns.model.buildings.Restaurant
import com.benjfletch.tinytowns.model.buildings.Shed
import com.benjfletch.tinytowns.model.buildings.Shop
import com.benjfletch.tinytowns.model.buildings.Tailor
import com.benjfletch.tinytowns.model.buildings.Tavern
import com.benjfletch.tinytowns.model.buildings.Temple
import com.benjfletch.tinytowns.model.buildings.Theater
import com.benjfletch.tinytowns.model.buildings.TradingPost
import com.benjfletch.tinytowns.model.buildings.UnfedCottage
import com.benjfletch.tinytowns.model.buildings.Warehouse
import com.benjfletch.tinytowns.model.buildings.Well
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.PolymorphicModuleBuilder
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

object Serializer {
    private fun PolymorphicModuleBuilder<Resource>.registerResources() {
        subclass(BRICK::class)
        subclass(GLASS::class)
        subclass(STONE::class)
        subclass(WOOD::class)
        subclass(WHEAT::class)
        subclass(ALL::class)
        subclass(NONE::class)
    }
    private fun PolymorphicModuleBuilder<Attraction>.registerAttractions() {
        subclass(Fountain::class)
        subclass(Millstone::class)
        subclass(Shed::class)
        subclass(Well::class)
    }
    private fun PolymorphicModuleBuilder<Cottage>.registerCottages() {
        subclass(UnfedCottage::class)
        subclass(FedCottage::class)
    }
    private fun PolymorphicModuleBuilder<FoodProducer>.registerFoodProducers() {
        subclass(Orchard::class)
        subclass(Granary::class)
        subclass(Farm::class)
        subclass(Greenhouse::class)
    }
    private fun PolymorphicModuleBuilder<GoodsHandler>.registerGoodHandlers() {
        subclass(Bank::class)
        subclass(Factory::class)
        subclass(TradingPost::class)
        subclass(Warehouse::class)
    }
    private fun PolymorphicModuleBuilder<PlaceOfWorship>.registerPlacesOfWorship() {
        subclass(Abbey::class)
        subclass(Cloister::class)
        subclass(Chapel::class)
        subclass(Temple::class)
    }
    private fun PolymorphicModuleBuilder<Restaurant>.registerRestaurants() {
        subclass(Almshouse::class)
        subclass(FeastHall::class)
        subclass(Inn::class)
        subclass(Tavern::class)
    }
    private fun PolymorphicModuleBuilder<Shop>.registerShops() {
        subclass(Bakery::class)
        subclass(Market::class)
        subclass(Tailor::class)
        subclass(Theater::class)
    }

    val Model = Json {
        serializersModule = SerializersModule {
            polymorphic(GamePiece::class) {
                subclass(EmptySpace::class)

                polymorphic(Resource::class) {
                    registerResources()
                }

                polymorphic(Building::class) {
                    polymorphic(Attraction::class) {
                        registerAttractions()
                    }
                    polymorphic(Cottage::class) {
                        registerCottages()
                    }
                    polymorphic(FoodProducer::class) {
                        registerFoodProducers()
                    }
                    polymorphic(GoodsHandler::class) {
                        registerGoodHandlers()
                    }
                    polymorphic(PlaceOfWorship::class) {
                        registerPlacesOfWorship()
                    }
                    polymorphic(Restaurant::class) {
                        registerRestaurants()
                    }
                    polymorphic(Shop::class) {
                        registerShops()
                    }
                    registerAttractions()
                    registerCottages()
                    registerFoodProducers()
                    registerGoodHandlers()
                    registerPlacesOfWorship()
                    registerRestaurants()
                    registerShops()
                }

                registerAttractions()
                registerCottages()
                registerFoodProducers()
                registerGoodHandlers()
                registerPlacesOfWorship()
                registerResources()
                registerRestaurants()
                registerShops()
            }
        }
    }
}
