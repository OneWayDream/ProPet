import { useEffect, useState } from "react"
import { useParams } from "react-router-dom"
import { getAccessToken, getFullUserInfo } from "../../../services/user.service"
import Template from "../template"

const SitterProfilePate = (props) => {
  const { sitterId } = useParams()
  const [sitter, setSitter] = useState({})
  const [isLoading, setIsLoading] = useState(true)

  useEffect(() => {
    setSitter(getFullUserInfo(sitterId, getAccessToken(), handleError, handleSuccess, "id"))
    setIsLoading(false)
  }, [])

  const handleError = (error) => {
    console.log("Error: " + error)
  }

  const handleSuccess = (response) => {
    console.log("Success: " + response)
  }

  const body =
    <>
      {isLoading ? 'Loading...' 
      :
      <div>test</div>
      }
    </>

  return <Template body={body} />
}

export default SitterProfilePate