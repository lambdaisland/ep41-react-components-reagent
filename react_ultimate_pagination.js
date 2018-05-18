

function Page(props) { return <button onClick={props.onClick}>{props.value}</button> }

function Ellipsis(props) { ... }
function FirstPageLink(props) { ... }
function PreviousPageLink(props) { ... }
function NextPageLink(props) { ... }
function LastPageLink(props) { ... }

function Wrapper(props) { return <div>{props.children}</div> }

var itemTypeToComponent = {
    'PAGE': Page,
    'ELLIPSIS': Ellipsis,
    'FIRST_PAGE_LINK': FirstPageLink,
    'PREVIOUS_PAGE_LINK': PreviousPageLink,
    'NEXT_PAGE_LINK': NextPageLink,
    'LAST_PAGE_LINK': LastPageLink
}

var UltimatePagination = ReactUltimatePagination.createUltimatePagination({
    itemTypeToComponent: itemTypeToComponent,
    WrapperComponent: Wrapper
})



function App () {
    return(
        <UltimatePagination currentPage=1 totalPages=5 onChange={{x=> ...}}>
        </UltimatePagination>
    )
}
