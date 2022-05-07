

const SearchItem = (props) => {
  const user = props.user
  console.log(user)
  return (<>
    <div className="searchItem">
      {user.name}
    </div>
  </>)
}

export default SearchItem