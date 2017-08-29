module MovieApi exposing (..)

import Http
import Json.Decode exposing (string, int, list, Decoder, at)
import Json.Decode.Pipeline exposing (decode, required, optional)


type alias Movie =
    { id : Int
    , poster : String
    , title : String
    }


type alias MovieDetail =
    { id : Int
    , title : String
    , plot : String
    , genre : String
    }


loadMovies : (Result Http.Error (List Movie) -> msg) -> Cmd msg
loadMovies msg =
    list movieDecoder
        |> Http.get "http://localhost:8080/api/v1/movies"
        |> Http.send msg


movieDecoder : Decoder Movie
movieDecoder =
    decode Movie
        |> required "id" int
        |> required "poster" string
        |> required "title" string


movieDetailDecoder : Decoder MovieDetail
movieDetailDecoder =
    decode MovieDetail
        |> required "id" int
        |> required "title" string
        |> required "plot" string
        |> required "genre" string


loadMovie : Int -> (Result Http.Error MovieDetail -> msg) -> Cmd msg
loadMovie id msg =
    movieDetailDecoder
        |> Http.get ("http://localhost:8080/api/v1/movies/" ++ (toString id))
        |> Http.send msg
