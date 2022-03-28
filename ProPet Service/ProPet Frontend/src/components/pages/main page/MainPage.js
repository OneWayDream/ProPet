import Template from "../template/Template";

const StyledMenuItem = styled.a((props) => `
	color: ${props.color ? props.color : 'black'}
`);

const MainPage = (props) => {
		
	const { children,  href, ...otherProps} = props;
	return <StyledMenuItem {...otherProps} href={href}>{children}</StyledMenuItem>
}


export default  MainPage;