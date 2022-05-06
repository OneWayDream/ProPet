import Image from "../../atoms/image/";
import bone from "../../../img/bone.png"
import top_pic from "../../../img/top.jpg"
import bot_pic from "../../../img/bottom.jpg"
import { Link } from "react-router-dom";
import paths from "../../../configs/paths";

const MainPageBody = () => {
  return (
    <div style={{ display: 'inline-flex' }}>
				<div style={{ textAlign: 'center' }} >
					<div style={{ fontSize: '2.7vw', textAlign: 'center', padding: '30px'}} >
						Временная семья для вашего питомца
					</div>
					<div style={{display: 'flex', alignItems: 'center'}}>
						<Image image={bone} width='5vw'/>
						<div style={{ fontSize: '1.5vw', padding: '20px 20px'}}>
							ProPet поможет найти человека, который присмотрит за питомцем во время вашего отъезда
						</div>
					</div>
					<div style={{display: 'flex', alignItems: 'center'}}>
						<Image image={bone} width='5vw'/>
						<div style={{ fontSize: '1.5vw', padding: '20px 20px'}}>
							Оставьте вашего любимца с профессиональной зооняней	
						</div>
					</div>
					<Link to={paths.SEARCH}><div className="mainPageBtn" style={{ color: "white" }}>Найти зооняню</div></Link>

					{/* <button className="mainPageBtn"><a href='/search' style={{ textDecoration: 'none', color: 'white' }}>Найти зооняню</a></button> */}
				</div>
				<div style={{  }}>
					<Image image={top_pic} width='35vw'/>	
					<Image image={bot_pic} width='35vw'/>
				</div>
			</div>
  )
}

export default MainPageBody;