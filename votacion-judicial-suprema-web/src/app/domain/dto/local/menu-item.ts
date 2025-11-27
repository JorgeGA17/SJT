export interface MenuItem {
    id?: string,
    label: string,
    icon?: string,
    routeLink: string,
    items?: MenuItem[],
    selected: boolean,
    command?:(event: Event) =>void
}

