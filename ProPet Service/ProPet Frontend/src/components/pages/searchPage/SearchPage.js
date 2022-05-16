import Template from "../template";
import SearchItem from "../../atoms/searchItem/";
import { useState } from "react";
import { searchSitters } from "../../../services/search.service";
import { getAccessToken } from "../../../services/user.service";
import { Link } from "react-router-dom";
import paths from "../../../configs/paths";

const SearchPage = () => {

  const [sitters, setSitters] = useState([])

  const handleSearch = () => {
    const JWT = getAccessToken()
    searchSitters(0, 10, "rating", "asc", JWT ? JWT: null, handleError, handleSuccess)
  }

  const handleSuccess = (response) => {
    setSitters(response)
  }

  const handleError = (response) => {
    alert("Что-то пошло не так")
    console.log("Error: " + response)
  }

  const body =
    <div style={{ padding: '0 1vw', display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center' }}>
      <div style={{ paddingRight: '1vw' }} >
        <div style={{ display: 'flex' }}>
          <div>
            <input type="text" className="searchInput" placeholder="Кот, собака" />
          </div>
          <div>
            <button className="submitButton" onClick={handleSearch}>Найти</button>
          </div>
        </div>
      </div>
      <div>
        {sitters ?
          sitters.map(sitter => {
            return (<Link to={paths.SITTER_PROFILE + '/' + sitter.id} style={{ color: 'black', textDecoration: 'none' }}><SearchItem sitter={sitter} /></Link>)
          })
          :
          <div style={{ paddingTop: '2vw' }}>Ничего не найдено</div>
        }
      </div>
    </div>
  return <Template body={body} />
}

export default SearchPage;