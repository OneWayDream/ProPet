import Image from "../../atoms/image";
import Template from "../template";
import FilterLogo from "../../../img/filter.png"
import SearchItem from "../../atoms/searchItem/";
import { useState } from "react";
import { searchSitters } from "../../../services/search.service";
import { getAccessToken } from "../../../services/user.service";

const SearchPage = () => {

  const [sitters, setSitters] = useState({})

  const showFilter = () => {
    console.log("AAA");
  }

  const handleSearch = () => {
    const JWT = getAccessToken()
    searchSitters(0, 10, "rating", "asc", JWT, handleError, handleSuccess)
  }

  const handleSuccess = (response) => {
    console.log("Success: " + JSON.stringify(response))
  }

  const handleError = (response) => {
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
        <SearchItem user={{ name: "name" }} />
      </div>
      {/* <div className="filter">
        <div onClick={showFilter}>
          <Image image={FilterLogo} width='4vw'/>
        </div>
        <div style={{ backgroundColor: 'red', }}>
        </div>
      </div> */}
    </div>
  return <Template body={body} />
}

export default SearchPage;