import Template from "../template/";
import MainPageBody from "../../molecules/MainPageBody/";

const MainPage = (props) => {
	const body = MainPageBody();
	return <Template body={body}/>
}	

export default  MainPage;