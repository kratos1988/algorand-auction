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
    bids: AuctionBid[];
    userId: number;
}

export interface AuctionDetails {
    item: Auction;
    bids: AuctionBid[];
}
