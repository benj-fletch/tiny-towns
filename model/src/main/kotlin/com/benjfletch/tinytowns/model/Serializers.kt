package com.benjfletch.tinytowns.model

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
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

private val module = SerializersModule {
    polymorphic(GamePiece::class) {
        subclass(EmptySpace::class)
        polymorphic(Resource::class) {
            subclass(ALL::class)
            subclass(BRICK::class)
            subclass(GLASS::class)
            subclass(STONE::class)
            subclass(WOOD::class)
            subclass(WHEAT::class)
            subclass(NONE::class)
        }
        polymorphic(Building::class) {
            polymorphic(Attraction::class) {
                subclass(Fountain::class)
                subclass(Millstone::class)
                subclass(Shed::class)
                subclass(Well::class)
            }
            polymorphic(Cottage::class) {
                subclass(FedCottage::class)
                subclass(UnfedCottage::class)
            }
            polymorphic(FoodProducer::class) {
                subclass(Orchard::class)
                subclass(Granary::class)
                subclass(Farm::class)
                subclass(Greenhouse::class)
            }
            polymorphic(GoodsHandler::class) {
                subclass(Bank::class)
                subclass(Factory::class)
                subclass(TradingPost::class)
                subclass(Warehouse::class)
            }
            polymorphic(PlaceOfWorship::class) {
                subclass(Abbey::class)
                subclass(Cloister::class)
                subclass(Chapel::class)
                subclass(Temple::class)
            }
            polymorphic(Restaurant::class) {
                subclass(Almshouse::class)
                subclass(FeastHall::class)
                subclass(Tavern::class)
                subclass(Inn::class)
            }
            polymorphic(Shop::class) {
                subclass(Bakery::class)
                subclass(Market::class)
                subclass(Tailor::class)
                subclass(Theater::class)
            }
        }
    }

}

fun getSerializer(): Json {
    return Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
        serializersModule = module
    }
}
