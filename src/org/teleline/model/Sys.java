package org.teleline.model;

import org.teleline.io.Validator;

public class Sys {
	
	public Sys(NetCollection nc, DFrameCollection dfc, CabinetCollection cbc, DBoxCollection dbc, ManholeCollection mc, DuctCollection duc, BuildingCollection buc, TubeCollection tuc, FrameCollection fc, BoxCollection bc, CableCollection cc, PairCollection pc, PathCollection phc, SubscriberCollection sc, DamageCollection dmc ) {
		
		this.nc = nc;
		this.dfc = dfc;
		this.cbc = cbc;
		this.dbc = dbc;
		this.mc = mc;
		this.duc = duc;
		this.buc = buc;
		this.tuc = tuc;
		this.fc = fc;
		this.bc = bc;
		this.cc = cc;
		this.pc = pc;
		this.phc = phc;
		this.sc = sc;
		this.dmc = dmc;
		this.v = new Validator();
		
	}
	
	public NetCollection nc;
	public DFrameCollection dfc;
	public CabinetCollection cbc; 
	public DBoxCollection dbc;
	public ManholeCollection mc;
	public DuctCollection duc;
	public BuildingCollection buc;
	public TubeCollection tuc;
	public FrameCollection fc;
	public BoxCollection bc;
	public CableCollection cc;
	public PairCollection pc;
	public PathCollection phc;
	public SubscriberCollection sc;
	public DamageCollection dmc;
	public Validator v;
	
}