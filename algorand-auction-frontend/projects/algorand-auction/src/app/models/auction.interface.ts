import {AuctionBid} from './bid.interface';

export interface Auction {
    description: string;
    expirationDate: string;
    highestBid: number;
    id: number;
    imageUrl: string;
    initialValue: number;
    itemName: string;
    title: string;
    userId: number;
    status: string;
}

export interface AuctionDetails {
    item: Auction;
    bids: AuctionBid[];
}
