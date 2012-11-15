package org.teleline.model;


import org.teleline.gui.gui;
import org.teleline.io.RW;
import org.teleline.io.Validator;

public class Sys {
	
	public Sys(/*NetCollection nc, DFrameCollection dfc, CabinetCollection cbc, DBoxCollection dbc, ManholeCollection mc, DuctCollection duc, BuildingCollection buc, TubeCollection tuc, FrameCollection fc, BoxCollection bc, CableCollection cc, PairCollection pc, PathCollection phc, SubscriberCollection sc, DamageCollection dmc */) {
		
		ig = new IdGenerator();
		nc = new NetCollection(ig);
		dfc = new DFrameCollection(ig);
		cbc = new CabinetCollection(ig);
		dbc = new DBoxCollection(ig);
		mc = new ManholeCollection(ig);
		duc = new DuctCollection(ig);
		buc = new BuildingCollection(ig);
		tuc = new TubeCollection(ig);
		fc = new FrameCollection(ig);
		bc = new BoxCollection(ig);
		cc = new CableCollection(ig);
		pc = new PairCollection(ig);
		phc = new PathCollection(ig);
		sc = new SubscriberCollection(ig);
		dmc = new DamageCollection(ig);
/*		
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
*/		
		this.v = new Validator();
		rw = new RW(ig,nc,dfc,cbc,dbc,mc,duc,buc,tuc,fc,bc,cc,pc,phc,sc,dmc);
	}
	
	public IdGenerator ig;
	public gui GUI;
	public RW rw;
	public Validator v;
	
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
	
	
}